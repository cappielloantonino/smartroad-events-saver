package it.almaviva.smartroadeventssaver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.etsi.Enum;
import it.almaviva.etsi.Etsi;
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
import it.almaviva.smartroadeventssaver.utils.CassandraException;
import it.almaviva.smartroadeventssaver.utils.KafkaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventSaver {

    @Autowired
    CassandraService cassandraService;

    @Autowired
    KafkaProducer kafkaProducer;


    public void menageDataFromAmqp(String messageJson) {
        try {
            // conversione json -> obj
            Denm messageObj = new ObjectMapper().readValue(messageJson, Denm.class);
            log.info("menageDataFromAmqp - message Object: {}", messageObj);
            System.out.println(messageObj);

            // scrittura kafka su topic out-denm
            log.info("menageDataFromAmqp - invio messaggio su topic Kafka");
            kafkaProducer.sendMessage(messageJson, Enum.MessageType.DENM);

            // scrittura cassandra su tabella denm
            log.info("menageDataFromAmqp - scrittura su DB Cassandra", messageObj);
            CassandraEventEntity cassandraEvent = CassandraEventsFactory.getFactory(messageObj).createCassandraEvent(messageJson);
            System.out.println(cassandraEvent);
            cassandraService.write(cassandraEvent);
        }
        catch(JsonProcessingException e) {
            log.error("menageDataFromAmqp - ERROR: errore durante la conversione del JSON in Object, potrebbe " +
                    "non trattarsi di un oggetto DENM valido", e);
        }
        catch(CassandraException e) {
            log.error("menageDataFromAmqp - ERROR", e);
        }
        catch(Exception e){
            log.error("menageDataFromAmqp - Generic Error", e);
        }
    }

    public void menageDataFromKafka(String messageJson, Enum.MessageType messageType) {
        try {
            // conversione json -> obj
            ObjectMapper objectMapper = new ObjectMapper();
            Etsi messageObj = null;
            switch (messageType) {
                case DENM: messageObj = objectMapper.readValue(messageJson, Denm.class); break;
                case IVIM: messageObj = objectMapper.readValue(messageJson, Ivim.class); break;
                default: break;
            }
            log.info("menageDataFromAmqp - message Object: {}", messageObj);

            // scrittura cassandra su tabella denm/ivim
            log.info("menageDataFromAmqp - scrittura su DB Cassandra", messageObj);
            CassandraEventEntity cassandraEvent = CassandraEventsFactory.getFactory(messageObj).createCassandraEvent(messageJson);
            System.out.println(cassandraEvent);
            cassandraService.write(cassandraEvent);
        }
        catch(JsonProcessingException e){
            log.error("menageDataFromAmqp - ERROR: errore durante la conversione del JSON in Object, potrebbe " +
                    "non trattarsi di un oggetto DENM valido", e);
        }
        catch(CassandraException e) {
            log.error("menageDataFromAmqp - ERROR", e);
        }
    }
}