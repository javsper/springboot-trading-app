package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.HistoricalMarketData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.repository.message.ErrorMessageRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;

public class HistoricalDataApiResponseChecker extends AbstractApiResponseChecker<HistoricalMarketData> {

    public HistoricalDataApiResponseChecker(IBKRDataTypeRepository<HistoricalMarketData> repository, RepositoryRefreshService repositoryRefreshService, ErrorMessageRepository errorMessageRepository) {
        super(repository, repositoryRefreshService, errorMessageRepository);
    }

    @Override
    protected boolean notInRepositoryOrError(Long id) {
        return repository.findByContractDataId(id.intValue()).isEmpty() && errorMessageRepository.findAllByErrorId(id.intValue()).isEmpty();
    }
}
