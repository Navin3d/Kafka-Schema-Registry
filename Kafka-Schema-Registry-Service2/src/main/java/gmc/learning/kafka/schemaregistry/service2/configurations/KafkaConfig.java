package gmc.learning.kafka.schemaregistry.service2.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
	
	private String serverUrl;
	
	private String topic;
	
	private String groupId;

}
