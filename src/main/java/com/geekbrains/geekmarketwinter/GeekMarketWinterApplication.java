package com.geekbrains.geekmarketwinter;

import com.geekbrains.geekmarketwinter.utils.MessageReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan("com.geekbrains.geekmarketwinter")
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class GeekMarketWinterApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GeekMarketWinterApplication.class, args);
		
		MessageReceiver.main(null);
	}
}