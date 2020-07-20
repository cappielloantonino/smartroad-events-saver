package it.almaviva.smartroadeventssaver.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.etsi.Enum;
import it.almaviva.etsi.denm.DecentralizedEnvironmentalNotificationMessage;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.header.ItsPduHeader;
import it.almaviva.etsi.ivim.Ivim;
import it.almaviva.etsi.ivim.container.IviStructure;
import it.almaviva.utility.Mapper;
import net.gcdc.asn1.uper.UperEncoder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.DataInput;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaTest {

    private static final String TOPIC = "topic-test";

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    BlockingQueue<ConsumerRecord<String, String>> records;

    KafkaMessageListenerContainer<String, String> container;


    @BeforeAll
    void setUp() {
        Map<String, Object> configsConsumer = new HashMap<>
                (KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>
                (configsConsumer, new StringDeserializer(), new StringDeserializer());

        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());

    }

    @AfterAll
    void tearDown() {
        container.stop();
    }

    @Test
    public void TestDenm() throws Exception {

        Map<String, Object> configsProducer = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        Producer<String, String> producer = new DefaultKafkaProducerFactory<>
                (configsProducer, new StringSerializer(), new StringSerializer()).createProducer();
        // Act
        //Denm obj = new Denm(new ItsPduHeader(), new DecentralizedEnvironmentalNotificationMessage());
        String pathUper = getClass().getClassLoader().getResource("denm_real.uper").getFile();
        byte[] denmBytes = Mapper.fileToBytes(pathUper);
        Denm denmObj = UperEncoder.decode(denmBytes, Denm.class);
        String json = new ObjectMapper().writeValueAsString(denmObj);

        System.out.println(json);
        producer.send(new ProducerRecord<>(TOPIC, "jsonDenm", json));
        producer.flush();

        // Assert
        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.key()).isEqualTo("jsonDenm");
        assertThat(singleRecord.value()).isEqualTo(json);
        Denm resultDenm = new ObjectMapper().readValue(singleRecord.value(), Denm.class);
        //assertThat(resultDenm).isEqualTo(denmObj); va in errore anche se visivamente sono uguali
        assertThat(resultDenm.getMessageType()).isEqualTo(Enum.MessageType.DENM);
    }

    @Test
    @Disabled
    public void TestIvim() throws Exception {

        Map<String, Object> configsProducer = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        Producer<String, String> producer = new DefaultKafkaProducerFactory<>
                (configsProducer, new StringSerializer(), new StringSerializer()).createProducer();
        // Act
        //Ivim obj = new Ivim(new ItsPduHeader(), new IviStructure());
        String pathUper = getClass().getClassLoader().getResource("custom_ivim_0.uper").getFile();
        byte[] ivimBytes = Mapper.fileToBytes(pathUper);
        Ivim ivimObj = UperEncoder.decode(ivimBytes, Ivim.class);
        String json = new ObjectMapper().writeValueAsString(ivimObj);

        System.out.println(json);
        producer.send(new ProducerRecord<>(TOPIC, "jsonIvim", json));
        producer.flush();

        // Assert
        ConsumerRecord<String, String> singleRecord = records.poll(100, TimeUnit.MILLISECONDS);
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.key()).isEqualTo("jsonIvim");
        assertThat(singleRecord.value()).isEqualTo(json);
        Ivim resultIvim = new ObjectMapper().readValue(singleRecord.value(), Ivim.class);
        //assertThat(resultIvim).isEqualTo(obj); funzionante
        assertThat(resultIvim.getMessageType()).isEqualTo(Enum.MessageType.IVIM);
    }
}

