package it.almaviva.smartroadeventssaver.kafka.consumer;

//import it.almaviva.smartroadeventssaver.cassandra.entity.TestEntity;
//import it.almaviva.smartroadeventssaver.cassandra.repository.TestRepository;
import it.almaviva.smartroadeventssaver.EventSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KafkaConsumer {
    @Autowired
    EventSaver eventSaver;

    @KafkaListener(topics = "${kafka.topic.in_ivim}", groupId = "smartroad")
    public void listenIVIM(String message) {
        System.out.println("CONSUMER IN-IVIM: " + message);

        eventSaver.menageDataFromKafka(message);
    }

    @KafkaListener(topics = "${kafka.topic.in_denm}", groupId = "smartroad")
    public void listenDENM(String message) {
        System.out.println("CONSUMER IN-DENM: " + message);

        eventSaver.menageDataFromKafka(message);
    }

    // SOLO PER TEST
    @KafkaListener(topics = "${kafka.topic.out_denm}", groupId = "smartroad")
    public void listenOutDENM(String message) {
        System.out.println("CONSUMER OUT-DENM: " + message);

        eventSaver.menageDataFromKafka(message);
    }

}
