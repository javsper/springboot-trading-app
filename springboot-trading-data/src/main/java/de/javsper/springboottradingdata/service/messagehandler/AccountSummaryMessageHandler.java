package de.javsper.springboottradingdata.service.messagehandler;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.AccountSummary;
import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountSummaryMessageHandler {

    private final KafkaTemplate<String, IBKRDataTypeEntity> kafkaEntityTemplate;
    private final PropertiesConfig propertiesConfig;

    public AccountSummaryMessageHandler(KafkaTemplate<String, IBKRDataTypeEntity> kafkaEntityTemplate, PropertiesConfig propertiesConfig) {
        this.kafkaEntityTemplate = kafkaEntityTemplate;
        this.propertiesConfig = propertiesConfig;
    }

    public void sendAccountSummaryMessage(AccountSummary accountSummary){
        kafkaEntityTemplate.send(propertiesConfig.getACCOUNT_SUMMARY_TOPIC(), accountSummary);
    }
}
