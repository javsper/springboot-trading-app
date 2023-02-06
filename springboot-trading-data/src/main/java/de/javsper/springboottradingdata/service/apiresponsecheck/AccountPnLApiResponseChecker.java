package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.KafkaConstantsConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.ProfitAndLossData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
public class AccountPnLApiResponseChecker extends AbstractApiResponseChecker<ProfitAndLossData> {

    public AccountPnLApiResponseChecker(IBKRDataTypeRepository<ProfitAndLossData> repository,
                                      RepositoryRefreshService repositoryRefreshService,
                                      KafkaApiCallEndService kafkaApiCallEndService,
                                      KafkaConstantsConfig kafkaConstantsConfig) {
            super(repository,repositoryRefreshService, kafkaApiCallEndService, kafkaConstantsConfig.getACCOUNT_PNL_TOPIC());
        }
}
