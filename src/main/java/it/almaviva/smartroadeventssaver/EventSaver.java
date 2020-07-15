package it.almaviva.smartroadeventssaver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.etsi.Enum;
import it.almaviva.etsi.denm.DecentralizedEnvironmentalNotificationMessage;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.header.ItsPduHeader;
import it.almaviva.smartroadeventssaver.cassandra.CassandraService;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.entity.factory.CassandraEventsFactory;
import it.almaviva.smartroadeventssaver.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventSaver {

    @Autowired
    CassandraService cassandraService;

    @Autowired
    KafkaProducer kafkaProducer;

    public void menageDataFromAmqp(String denmMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //Denm obj = objectMapper.readValue(denmMessage, Denm.class);
        Denm obj = new Denm(new ItsPduHeader(), new DecentralizedEnvironmentalNotificationMessage());


        // scrittura cassandra su tabella denm
        CassandraEventEntity cassandraEvent = CassandraEventsFactory.getFactory(obj).createCassandraEvent(denmMessage);
        System.out.println(cassandraEvent);
        //cassandraService.write(cassandraEvent);

        // scrittura kafka su topic out-denm
        kafkaProducer.sendMessage(Enum.MessageType.DENM, denmMessage);
    }

    public void menageDataFromKafka(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        //Denm obj = objectMapper.readValue(denmMessage, Denm.class);
        //Denm obj = new Denm(new ItsPduHeader(), new DecentralizedEnvironmentalNotificationMessage());

        // scrittura cassandra su tabella
        //CassandraEventEntity cassandraEvent = CassandraEventsFactory.getFactory(obj).createCassandraEvent(message);
        //System.out.println(cassandraEvent);
        //cassandraService.write(cassandraEvent);
    }

}