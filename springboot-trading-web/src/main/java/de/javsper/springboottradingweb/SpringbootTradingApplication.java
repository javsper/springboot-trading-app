package de.javsper.springboottradingweb;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.javsper")
@EntityScan(basePackages = "de.javsper")
@ComponentScan(basePackages = "de.javsper")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableScheduling
public class SpringbootTradingApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringbootTradingApplication.class, args);

		PropertiesConfig propertiesConfig = context.getBean(PropertiesConfig.class);
		ConnectionInitiator connection = context.getBean(ConnectionInitiator.class);
		connection.connect(propertiesConfig.getTradingPort());
	}

}
