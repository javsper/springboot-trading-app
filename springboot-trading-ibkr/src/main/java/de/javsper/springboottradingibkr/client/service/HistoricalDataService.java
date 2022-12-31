package de.javsper.springboottradingibkr.client.service;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.HistoricalMarketData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.repository.HistoricalMarketDataRepository;
import de.javsper.springboottradingibkr.client.datamodel.HistoricalDataSettings;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoricalDataService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final EClientSocket client;
    private final HistoricalMarketDataRepository historicalMarketDataRepository;
    private ContractDataToIBKRContract contractDataToIBKRContract;

    public HistoricalDataService(UniqueContractDataProvider uniqueContractDataProvider, EClientSocket client, HistoricalMarketDataRepository historicalMarketDataRepository, ContractDataToIBKRContract contractDataToIBKRContract) {
        this.uniqueContractDataProvider = uniqueContractDataProvider;
        this.client = client;
        this.historicalMarketDataRepository = historicalMarketDataRepository;
        this.contractDataToIBKRContract = contractDataToIBKRContract;
    }

    public List<HistoricalMarketData> requestHistoricalData(ContractData contractData, HistoricalDataSettings settings) {
        Optional<ContractData> contractDataOptional = uniqueContractDataProvider.getExistingContractDataOrCallApi(contractData);
        contractDataOptional.ifPresent((savedContractData) -> {
           call(savedContractData,settings);
        });
        return new ArrayList<>();
    }

    private void call(ContractData contractData, HistoricalDataSettings settings){
        client.reqHistoricalData(contractData.getContractId(),
                contractDataToIBKRContract.convertContractData(contractData),
                settings.getBackfillEndTime().toString(),
                settings.getBackfillDuration(),
                settings.getBarSizeSetting(),
                settings.getWhatToShow().toString(),
                settings.isRegularTradingHours()?1:0,
                settings.getDateFormatStyle(),
                settings.isKeepUpToDate(),
                settings.getChartOptions());
    }
}
