package com.smarthome.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@ComponentScan("com.smarthome.server.controllers")
@ComponentScan("com.smarthome.server.entities")
@ComponentScan("com.smarthome.server.repositories")
@EnableJpaRepositories
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ServerApplication {
	private static final String SSL_KEY = "javax.net.ssl.trustStore";
	private static final String SSL_VALUE = System.getProperty("user.dir") + "\\secret_key";//"C:\\Program Files\\Java\\jdk1.8.0_144\\bin\\secret_key";

	public static void main(String[] args) {
		System.setProperty(SSL_KEY, SSL_VALUE);
		SpringApplication.run(ServerApplication.class, args);
	}
}