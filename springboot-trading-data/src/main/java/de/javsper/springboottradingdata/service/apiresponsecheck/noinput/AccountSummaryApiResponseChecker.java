package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.AccountSummaryData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
public class AccountSummaryApiResponseChecker extends AbstractNoInputListApiResponseChecker<AccountSummaryData> {


    public AccountSummaryApiResponseChecker(RepositoryRefreshService repositoryRefreshService,
                                            IBKRDataTypeRepository<AccountSummaryData> repository,
                                            PropertiesConfig propertiesConfig,
                                            KafkaApiCallEndService kafkaApiCallEndService) {
        super(repositoryRefreshService, repository, propertiesConfig.getACCOUNT_SUMMARY_ID(), kafkaApiCallEndService);
    }
}