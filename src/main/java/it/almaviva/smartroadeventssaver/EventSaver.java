package it.almaviva.smartroadeventssaver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.etsi.Enum;
import it.almaviva.etsi.common.TimestampIts;
import it.almaviva.etsi.denm.DecentralizedEnvironmentalNotificationMessage;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.header.ItsPduHeader;
import it.almaviva.etsi.ivim.Ivim;
import it.almaviva.etsi.ivim.container.*;
import it.almaviva.smartroadeventssaver.cassandra.CassandraService;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraIvim;
import it.almaviva.smartroadeventssaver.cassandra.entity.factory.CassandraEventsFactory;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraDenmRepository;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraIvimRepository;
import it.almaviva.smartroadeventssaver.kafka.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventSaver {

    @Autowired
    CassandraService cassandraService;

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    CassandraIvimRepository cassandraIvimRepository;

    public void menageDataFromAmqp(String denmMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Denm obj = objectMapper.readValue(denmMessage, Denm.class);
        //Denm obj = new Denm(new ItsPduHeader(), new DecentralizedEnvironmentalNotificationMessage());
        System.out.println(obj);

        // scrittura cassandra su tabella denm
        CassandraEventEntity cassandraEvent = CassandraEventsFactory.getFactory(obj).createCassandraEvent(denmMessage);
        System.out.println(cassandraEvent);
        cassandraService.write(cassandraEvent);

        // scrittura kafka su topic out-denm
        kafkaProducer.sendMessage(Enum.MessageType.DENM, denmMessage);
    }

    public void menageDataFromKafka(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        //Denm obj = objectMapper.readValue(denmMessage, Denm.class);
        //Denm obj = new Denm(new ItsPduHeader(), new DecentralizedEnvironmentalNotificationMessage());

        // ItsPduHeader
        ItsPduHeader itsPduHeader = new ItsPduHeader();
        // IviStructure
        CountryCode countryCode = new CountryCode();
        IssuerIdentifier providerIdentifier = new IssuerIdentifier(12458);
        Provider provider = new Provider(countryCode, providerIdentifier);
        IviIdentificationNumber iviIdentificationNumber = new IviIdentificationNumber(3147);
        IviStatus iviStatus = new IviStatus(5);
        IVIManagementContainer mandatory = new IVIManagementContainer(provider, iviIdentificationNumber, iviStatus);
        mandatory.setValidFrom(new TimestampIts(2000));
        mandatory.setValidTo(new TimestampIts(2000));
        IviStructure iviStructure = new IviStructure(mandatory, null);

        Ivim obj = new Ivim(itsPduHeader, iviStructure);
        obj.setMessageType(Enum.MessageType.IVIM);

        // scrittura cassandra su tabella
        CassandraEventEntity cassandraEvent = CassandraEventsFactory.getFactory(obj).createCassandraEvent(message);
        System.out.println(cassandraEvent);
        cassandraIvimRepository.insert((CassandraIvim) cassandraEvent);
    }

}