package de.javsper.springboottradingibkr.client.config;

import com.ib.client.EClientSocket;
import com.ib.client.EJavaSignal;
import de.javsper.springboottradingibkr.client.IBKRConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan("de.javsper.springboottradingibkr")
public class IBKRConfiguration {

    @Bean
    public EJavaSignal eJavaSignal(){
        return new EJavaSignal();
    }

    @Bean
    public EClientSocket eClientSocket(EJavaSignal eJavaSignal, IBKRConnection ibkrConnection) {
        return new EClientSocket(ibkrConnection,eJavaSignal);
    }
    @Bean
    public TaskExecutor getTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(1);
        return threadPoolTaskExecutor;
    }

}
