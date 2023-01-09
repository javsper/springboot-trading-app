package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.config.KafkaConstantsConfig;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.AccountSummaryData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountSummaryApiResponseChecker extends AbstractNoInputListApiResponseChecker<AccountSummaryData> {


    public AccountSummaryApiResponseChecker(IBKRDataTypeRepository<AccountSummaryData> repository,
                                            PropertiesConfig propertiesConfig,
                                            KafkaApiCallEndService kafkaApiCallEndService, KafkaConstantsConfig kafkaConstantsConfig) {
        super(repository, propertiesConfig.getACCOUNT_SUMMARY_ID(), kafkaApiCallEndService,
                kafkaConstantsConfig.getACCOUNT_SUMMARY_TOPIC());
    }
}