package de.javsper.springboottradingibkr.client.service.historicaldata;

import de.javsper.springboottradingdata.model.data.entity.HistoricalData;
import de.javsper.springboottradingdata.model.data.HistoricalDataSettings;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricalDataService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final HistoricalResponseListService historicalResponseListService;


    public List<HistoricalData> requestHistoricalData(HistoricalDataSettings settings) {
        return uniqueContractDataProvider.getExistingContractDataOrCallApi(settings.getContractData())
                .map((uniqueContractData) -> {
                    settings.setContractData(uniqueContractData);
                    return historicalResponseListService.getResponseList(settings);
                }).orElseGet(ArrayList::new);
    }


}
