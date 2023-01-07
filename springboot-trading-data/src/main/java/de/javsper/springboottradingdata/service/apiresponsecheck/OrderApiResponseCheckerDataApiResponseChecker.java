package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
class OrderApiResponseCheckerDataApiResponseChecker extends AbstractApiResponseCheckerApiResponseChecker<OrderData> {

    public OrderApiResponseCheckerDataApiResponseChecker(IBKRDataTypeRepository<OrderData> repository,
                                                         RepositoryRefreshService repositoryRefreshService,
                                                         KafkaApiCallEndService kafkaApiCallEndService) {
        super(repository, repositoryRefreshService, kafkaApiCallEndService);
    }
}
