package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.KafkaConstantsConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.OrderData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
class OrderDataApiResponseChecker extends AbstractApiResponseChecker<OrderData> {

    public OrderDataApiResponseChecker(IBKRDataTypeRepository<OrderData> repository,
                                       RepositoryRefreshService repositoryRefreshService,
                                       KafkaApiCallEndService kafkaApiCallEndService,
                                       KafkaConstantsConfig kafkaConstantsConfig) {
        super(repository, repositoryRefreshService, kafkaApiCallEndService, kafkaConstantsConfig.getORDER_TOPIC());
    }
}
