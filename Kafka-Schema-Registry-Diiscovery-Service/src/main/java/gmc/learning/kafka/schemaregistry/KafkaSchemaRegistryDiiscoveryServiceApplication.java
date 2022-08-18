package gmc.learning.kafka.schemaregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class KafkaSchemaRegistryDiiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSchemaRegistryDiiscoveryServiceApplication.class, args);
	}

}
