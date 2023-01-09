package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.config.KafkaConstantsConfig;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.PositionData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class PositionApiResponseChecker extends AbstractNoInputListApiResponseChecker<PositionData> {


    public PositionApiResponseChecker(IBKRDataTypeRepository<PositionData> repository,
                                      PropertiesConfig propertiesConfig,
                                      KafkaApiCallEndService kafkaApiCallEndService,
                                      KafkaConstantsConfig kafkaConstantsConfig) {
        super(repository, propertiesConfig.getPOSITION_CALL_ID(), kafkaApiCallEndService,
                kafkaConstantsConfig.getPOSITION_TOPIC());
    }
}
