package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.PositionData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionApiResponseChecker implements NoInputListApiResponseChecker<PositionData> {

    private final RepositoryRefreshService repositoryRefreshService;
    private final IBKRDataTypeRepository<PositionData> repository;
    private final PropertiesConfig propertiesConfig;
    private final KafkaApiCallEndService kafkaApiCallEndService;

    public PositionApiResponseChecker(RepositoryRefreshService repositoryRefreshService,
                                      IBKRDataTypeRepository<PositionData> repository,
                                      PropertiesConfig propertiesConfig,
                                      KafkaApiCallEndService kafkaApiCallEndService) {
        this.repositoryRefreshService = repositoryRefreshService;
        this.repository = repository;
        this.propertiesConfig = propertiesConfig;
        this.kafkaApiCallEndService = kafkaApiCallEndService;
    }

    @Override
    public List<PositionData> checkForApiResponseAndUpdate() {
        kafkaApiCallEndService.waitForApiCallToFinish(propertiesConfig.getPOSITION_CALL_ID());
        repositoryRefreshService.clearCache(repository);

        List<PositionData> positions = new ArrayList<>();
        repository.findAll().forEach(positions::add);
        return positions;
    }
}
