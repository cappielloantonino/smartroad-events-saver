package it.almaviva.smartroadeventssaver.kafka.consumer;

import it.almaviva.smartroadeventssaver.cassandra.entity.TestEntity;
import it.almaviva.smartroadeventssaver.cassandra.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MessageConsumer {
    @Autowired
    TestRepository testRepository;

    @KafkaListener(topics = "${kafka.topic1}", groupId = "smartroad")
    public void listen(String message) {
        System.out.println("CONSUMER: " + message);

        testRepository.insert(new TestEntity(message, new Random().nextInt(90)));
    }
}
