package de.javsper.springboottradingweb.websocket;

import de.javsper.springboottradingdata.config.KafkaConstantsConfig;
import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.model.entity.PositionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PortfolioKafkaConsumer {

    private final KafkaConstantsConfig kafkaConstantsConfig;
    private final SimpMessagingTemplate messagingTemplate;

    public PortfolioKafkaConsumer(KafkaConstantsConfig kafkaConstantsConfig, SimpMessagingTemplate messagingTemplate) {
        this.kafkaConstantsConfig = kafkaConstantsConfig;
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "${spring.kafka.names.topic.positions}")
    public void consumeMessage(IBKRDataTypeEntity message){
        PositionData position = (PositionData) message;
        log.warn("Message received: " + position.getId());
        messagingTemplate.convertAndSend("/topic/"+kafkaConstantsConfig.getPOSITION_TOPIC(), position);
    }
}
