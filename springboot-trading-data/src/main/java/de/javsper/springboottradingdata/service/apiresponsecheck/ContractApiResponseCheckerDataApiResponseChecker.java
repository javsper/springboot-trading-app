package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
class ContractApiResponseCheckerDataApiResponseChecker extends AbstractApiResponseCheckerApiResponseChecker<ContractData> {

    public ContractApiResponseCheckerDataApiResponseChecker(IBKRDataTypeRepository<ContractData> repository,
                                                            RepositoryRefreshService repositoryRefreshService,
                                                            KafkaApiCallEndService kafkaApiCallEndService) {
        super(repository,repositoryRefreshService, kafkaApiCallEndService);
    }
}
