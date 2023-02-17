package de.javsper.springboottradingibkr.client.service.marketdata;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StopMarketDataService {

    private final PropertiesConfig propertiesConfig;
    private final StopMarketDataApiCaller stopMarketDataApiCaller;
    private final ContractDataRepository contractDataRepository;

    public StopMarketDataService(PropertiesConfig propertiesConfig, StopMarketDataApiCaller stopMarketDataApiCaller,
                                 ContractDataRepository contractDataRepository) {
        this.propertiesConfig = propertiesConfig;
        this.stopMarketDataApiCaller = stopMarketDataApiCaller;
        this.contractDataRepository = contractDataRepository;
    }

    public void stopMarketDataForContractId(int id) {
        contractDataRepository.findById((long) id).ifPresent((contractData) -> {
                    stopMarketDataApiCaller.callApi(id);
                }
        );

    }

    //TODO does not work this way anymore maybe
    public List<ContractData> stopAllMarketData() {
        List<ContractData> active = new ArrayList<>();
        propertiesConfig.getActiveMarketData().forEach((id) -> {
            stopMarketDataApiCaller.callApi(id);
            active.addAll(contractDataRepository.findAllByContractId(id));
        });
        return active;
    }
}
