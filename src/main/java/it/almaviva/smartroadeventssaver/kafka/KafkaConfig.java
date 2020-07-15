package it.almaviva.smartroadeventssaver.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {
    //@Value(value = "${kafka.bootstrapAddress}")
    //private String bootstrapAddress;

    @Value(value = "${kafka.topic.out_denm}")
    private String topic_out_denm;

    @Value(value = "${kafka.topic.in_ivim}")
    private String topic_in_ivim;

    @Value(value = "${kafka.topic.in_denm}")
    private String topic_in_denm;

    @Value(value = "${kafka.topic.retention-ms-config}")
    private String retention_ms_config;

    /*Spring dovrebbe crearlo in automatico
    Per settare impostazioni personalizzate utilizzare AdminClient
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        return new KafkaAdmin(configs);
    }*/

    @Bean
    public NewTopic out_denm() {
        return TopicBuilder.name(topic_out_denm)
                .configs(topicConfigs())
                .build();
    }

    @Bean
    public NewTopic in_denm() {
        return TopicBuilder.name(topic_in_denm)
                .configs(topicConfigs())
                .build();
    }

    @Bean
    public NewTopic in_ivim() {
        return TopicBuilder.name(topic_in_ivim)
                .configs(topicConfigs())
                .build();
    }

    @Bean
    public Map<String, String> topicConfigs() {
        Map<String, String> props = new HashMap<>();
        props.put(TopicConfig.RETENTION_MS_CONFIG, retention_ms_config);
        return props;
    }

    /*Partition e replicas si pososno omettere se valgono 1.
    '.compact' set the TopicConfig.CLEANUP_POLICY_CONFIG to TopicConfig.CLEANUP_POLICY_COMPACT.
     I messaggi non hanno limite di tempo e di spazio (non esiste un periodo massimo di fruizione, nè un limite di salvataggio)*/
}
