package de.javsper.springboottradingibkr.client.service.marketdata;

import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StopAndStartMarketDataService {

    private final ContractDataRepository contractDataRepository;
    private final  @Qualifier("StartMarketDataApiCaller") ApiCaller<ContractData> startMarketDataApiCaller;
    private final StopMarketDataService stopMarketDataService;

    public void reinitiateApiCall(int id){
        stopMarketDataService.stopMarketDataForContractId(id);
        contractDataRepository.findById((long)id).ifPresent(
                startMarketDataApiCaller::callApi);
    }
}
