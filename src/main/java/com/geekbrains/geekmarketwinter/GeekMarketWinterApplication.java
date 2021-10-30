package com.geekbrains.geekmarketwinter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@SpringBootApplication
@ComponentScan("com.geekbrains.geekmarketwinter")
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class GeekMarketWinterApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeekMarketWinterApplication.class, args);
	}
}