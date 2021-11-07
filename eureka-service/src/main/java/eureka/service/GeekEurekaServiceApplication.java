package eureka.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GeekEurekaServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeekEurekaServiceApplication.class, args);
	}
}