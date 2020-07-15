package it.almaviva.smartroadeventssaver.kafka.producer;

import it.almaviva.etsi.Enum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate producer;

    @Value(value = "${kafka.topic.out_denm}") //out_denm
    private String topic_denm;


    public void sendMessage(Enum.MessageType messageType, String message) {

        String topicName = null;
        switch (messageType) {
            case DENM: topicName = topic_denm; break;
            case IVIM: break;
            case CAM: break;
            default: break;
        }

        ListenableFuture<SendResult<String, String>> future = producer.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("PRODUCER - Sent on Topic [" + result.getProducerRecord().topic() + "], message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("PRODUCER - Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}