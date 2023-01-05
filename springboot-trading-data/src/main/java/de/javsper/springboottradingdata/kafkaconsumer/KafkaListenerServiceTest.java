package de.javsper.springboottradingdata.kafkaconsumer;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenerServiceTest {

//    @KafkaListener(groupId = "marketDataGroup",topics = "historical1")
//    public void handleMessage(String msg){
//      log.warn(msg);
//    }
}
