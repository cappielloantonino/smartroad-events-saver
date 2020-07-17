package it.almaviva.smartroadeventssaver.kafka.consumer;

//import it.almaviva.smartroadeventssaver.cassandra.entity.TestEntity;
//import it.almaviva.smartroadeventssaver.cassandra.repository.TestRepository;
import it.almaviva.etsi.Enum;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.ivim.Ivim;
import it.almaviva.smartroadeventssaver.EventSaver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class KafkaConsumer{

    @Autowired
    EventSaver eventSaver;

    @Value(value = "${kafka.topic.in_ivim}")
    private String topic_in_ivim;

    @Value(value = "${kafka.topic.in_denm}")
    private String topic_in_denm;

    @KafkaListener(
            topicPartitions = {@TopicPartition(topic = "${kafka.topic.in_ivim}", partitions = "0"),
                            @TopicPartition(topic = "${kafka.topic.in_denm}", partitions = "0"),
                            @TopicPartition(topic = "${kafka.topic.out_denm}", partitions = "0")},
            groupId = "smartroad")
    public void listen(@Payload String  message,
                       @Header(KafkaHeaders.PARTITION_ID) int partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.GROUP_ID) String groupId,
                       @Header(KafkaHeaders.OFFSET) long offset)
    {
        log.info("KAFKA-CONSUMER[{}] - Consume topic=[{}], partition=[{}], offset=[{}], groupId=[{}]",
                topic, topic, partition, offset, groupId);
        log.info("KAFKA-CONSUMER[{}] - Message: {}", topic, message);

        if(topic.equalsIgnoreCase(topic_in_denm))
            eventSaver.menageDataFromKafka(message, Enum.MessageType.DENM);
        else if(topic.equalsIgnoreCase(topic_in_denm)) {
            eventSaver.menageDataFromKafka(message, Enum.MessageType.IVIM);
        }
    }
}