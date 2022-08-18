package gmc.learning.kafka.schemaregistry.service1.configurations;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import gmc.learning.kafka.avro.schema.TestTemplate;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
	
	@Autowired
	private KafkaConfig kafkaConfigs;
	
	public Map<String, Object> getProducerConfiguration() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigs.getServerUrl());
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
		configs.put("key.converter.schema.registry.url", "http://localhost:8081");
		configs.put("value.converter.schema.registry.url", "http://localhost:8081");
		return configs;
	}
	
	public ProducerFactory<String, TestTemplate> getProducer() {
		return new DefaultKafkaProducerFactory<>(getProducerConfiguration());
	}
	
	@Bean
	public KafkaTemplate<String, TestTemplate> kafkaTemplate() {
		return new KafkaTemplate<String, TestTemplate>(getProducer());
	}

}
