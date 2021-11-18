package com.geekbrains.geekmarketwinter;

import com.geekbrains.geekmarketwinter.utils.MessageReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableFeignClients
@EntityScan({"contract.entities", "com.geekbrains.geekmarketwinter.entities"})

public class GeekMarketWinterApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GeekMarketWinterApplication.class, args);
		
		MessageReceiver.main(null);
	}
}