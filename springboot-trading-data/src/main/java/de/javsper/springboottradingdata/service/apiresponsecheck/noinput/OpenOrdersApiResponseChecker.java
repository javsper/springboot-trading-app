package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.config.KafkaConstantsConfig;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.OrderData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class OpenOrdersApiResponseChecker extends AbstractNoInputListApiResponseChecker<OrderData> {


    public OpenOrdersApiResponseChecker(IBKRDataTypeRepository<OrderData> repository,
                                        PropertiesConfig propertiesConfig,
                                        KafkaApiCallEndService kafkaApiCallEndService,
                                        KafkaConstantsConfig kafkaConstantsConfig) {
        super(repository, propertiesConfig.getOPEN_ORDERS_ID(), kafkaApiCallEndService,
                kafkaConstantsConfig.getOPEN_ORDER_TOPIC());
    }
}