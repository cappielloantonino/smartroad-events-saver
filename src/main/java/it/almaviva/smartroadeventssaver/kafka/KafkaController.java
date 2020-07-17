package it.almaviva.smartroadeventssaver.kafka;

import it.almaviva.etsi.Enum;
import it.almaviva.smartroadeventssaver.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping("/produce")
    public Boolean produce(@RequestParam String message) {
        kafkaProducer.sendMessage(message, Enum.MessageType.IVIM);

        return true;
    }
}
