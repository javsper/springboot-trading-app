package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.PositionData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
public class PositionApiResponseChecker extends AbstractNoInputListApiResponseChecker<PositionData> {


    public PositionApiResponseChecker(RepositoryRefreshService repositoryRefreshService,
                                      IBKRDataTypeRepository<PositionData> repository,
                                      PropertiesConfig propertiesConfig,
                                      KafkaApiCallEndService kafkaApiCallEndService) {
        super(repositoryRefreshService, repository, propertiesConfig.getPOSITION_CALL_ID(), kafkaApiCallEndService);
    }
}
