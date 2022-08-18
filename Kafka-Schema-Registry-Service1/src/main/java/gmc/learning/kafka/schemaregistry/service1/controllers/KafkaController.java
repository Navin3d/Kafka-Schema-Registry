package gmc.learning.kafka.schemaregistry.service1.controllers;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.kafka.avro.schema.TestTemplate;
//import gmc.learning.kafka.avro.schema.TestTemplate;
import gmc.learning.kafka.schemaregistry.service1.configurations.KafkaConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/kafka")
public class KafkaController {
	
	@Autowired
	private KafkaConfig kafkaConfig;
	
	@Autowired
	private KafkaTemplate<String, gmc.learning.kafka.avro.schema.TestTemplate> restTemplate;
	
	@GetMapping(path = "/{message}")
	private String sendMessage(@PathVariable String message) {
		gmc.learning.kafka.avro.schema.TestTemplate tst = gmc.learning.kafka.avro.schema.TestTemplate.newBuilder()
															.setId("111")
															.setMessage(message).build();
//		restTemplate.send(kafkaConfig.getTopic(), tst);
		sendMsg(message);
		return "sent!.";
	}
	
	private TestTemplate sendMsg(String message)	{
		
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", kafkaConfig.getBootstrapUrl());
		properties.setProperty("acks", "all");
		properties.setProperty("retries", "10");
		
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
		properties.setProperty("schema.registry.url", kafkaConfig.getRegistryUrl());
		
		Producer<String, TestTemplate> producer = new KafkaProducer<String, TestTemplate>(properties);

		TestTemplate msg = TestTemplate.newBuilder()
        		.setId("OId234")
        		.setMessage(message)
                .build();

        ProducerRecord<String, TestTemplate> producerRecord = new ProducerRecord<String, TestTemplate>(kafkaConfig.getTopic(), msg);

        
        producer.send(producerRecord, new Callback() {
        	@Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    log.info(metadata.toString()); 
                } else {
                	log.error(exception.getMessage());
                }
            }
        });

        producer.flush();
        producer.close();
        
        return msg;
	}

}
