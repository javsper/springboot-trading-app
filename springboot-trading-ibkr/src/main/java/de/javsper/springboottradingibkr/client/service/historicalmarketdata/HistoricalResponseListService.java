package de.javsper.springboottradingibkr.client.service.historicalmarketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.service.IBKRTimeStampFormatter;
import de.javsper.springboottradingdata.service.apiresponsecheck.HistoricalMarketDataApiResponseChecker;
import de.javsper.springboottradingibkr.client.datamodel.HistoricalDataSettings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricalResponseListService {

    private final PropertiesConfig propertiesConfig;
    private final HistoricalMarketDataApiResponseChecker historicalMarketDataApiResponseChecker;
    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final IBKRTimeStampFormatter ibkrTimeStampFormatter;
    private final HistoricalDataApiCaller historicalDataApiCaller;

    public HistoricalResponseListService(PropertiesConfig propertiesConfig, HistoricalMarketDataApiResponseChecker historicalMarketDataApiResponseChecker, EClientSocket client, ContractDataToIBKRContract contractDataToIBKRContract, IBKRTimeStampFormatter ibkrTimeStampFormatter, HistoricalDataApiCaller historicalDataApiCaller) {
        this.propertiesConfig = propertiesConfig;
        this.historicalMarketDataApiResponseChecker = historicalMarketDataApiResponseChecker;
        this.client = client;
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.ibkrTimeStampFormatter = ibkrTimeStampFormatter;
        this.historicalDataApiCaller = historicalDataApiCaller;
    }

    /**
     * Properties Flag active API Calls set because the Api is called in a Thread that cannot be accessed.
     *
     * @param settings Settings for the Historical Data
     * @param contractData Contract that historical Data will be called off of
     */
    public List<IBKRDataTypeEntity> getResponseList(HistoricalDataSettings settings, ContractData contractData) {
        propertiesConfig.addToActiveApiCalls((long) contractData.getContractId());
        historicalDataApiCaller.callApi(contractData,settings);
        return (historicalMarketDataApiResponseChecker.checkForApiResponseAndUpdate(contractData.getContractId()));
    }
}
