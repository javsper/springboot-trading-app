package de.javsper.springboottradingweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.javsper")
@EntityScan(basePackages = "de.javsper")
@ComponentScan(basePackages = "de.javsper")
public class SpringbootTradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootTradingApplication.class, args);
	}

}
