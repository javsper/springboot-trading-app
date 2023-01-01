package de.javsper.springboottradingibkr.client.service.historicalmarketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.service.IBKRTimeStampFormatter;
import de.javsper.springboottradingdata.service.apiresponsecheck.HistoricalMarketDataApiResponseChecker;
import de.javsper.springboottradingibkr.client.datamodel.HistoricalDataSettings;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoricalMarketDataService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final HistoricalResponseListService historicalResponseListService;

    public HistoricalMarketDataService(UniqueContractDataProvider uniqueContractDataProvider, EClientSocket client, HistoricalMarketDataApiResponseChecker historicalMarketDataApiResponseChecker, ContractDataToIBKRContract contractDataToIBKRContract, IBKRTimeStampFormatter ibkrTimeStampFormatter, PropertiesConfig propertiesConfig, HistoricalResponseListService historicalResponseListService) {
        this.uniqueContractDataProvider = uniqueContractDataProvider;
        this.historicalResponseListService = historicalResponseListService;
    }

    public List<IBKRDataTypeEntity> requestHistoricalData(ContractData contractData, HistoricalDataSettings settings) {
        List<IBKRDataTypeEntity> historicalDataTicks = new ArrayList<>();
        Optional<ContractData> contractDataOptional = uniqueContractDataProvider.getExistingContractDataOrCallApi(contractData);
        contractDataOptional.ifPresent((savedContractData) -> {
           historicalDataTicks.addAll(historicalResponseListService.getResponseList(settings, savedContractData));
        });

        return historicalDataTicks;
    }


}
