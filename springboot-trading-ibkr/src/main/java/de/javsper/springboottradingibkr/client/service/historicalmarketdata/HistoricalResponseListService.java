package de.javsper.springboottradingibkr.client.service.historicalmarketdata;

import de.javsper.springboottradingdata.model.HistoricalMarketData;
import de.javsper.springboottradingdata.service.apiresponsecheck.HistoricalMarketDataApiResponseChecker;
import de.javsper.springboottradingibkr.client.datamodel.HistoricalDataSettings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricalResponseListService {

    private final HistoricalMarketDataApiResponseChecker historicalMarketDataApiResponseChecker;
    private final HistoricalDataApiCaller historicalDataApiCaller;

    public HistoricalResponseListService(HistoricalMarketDataApiResponseChecker historicalMarketDataApiResponseChecker, HistoricalDataApiCaller historicalDataApiCaller) {
        this.historicalMarketDataApiResponseChecker = historicalMarketDataApiResponseChecker;
        this.historicalDataApiCaller = historicalDataApiCaller;
    }

    /**
     * Properties Flag active API Calls set because the Api is called in a Thread that cannot be accessed.
     *
     * @param settings Settings for the Historical Data
     * @param contractData Contract that historical Data will be called off of
     */
    public List<HistoricalMarketData> getResponseList(HistoricalDataSettings settings) {

        historicalDataApiCaller.callApi(settings);
        return historicalMarketDataApiResponseChecker.checkForApiResponseAndUpdate(settings.getContractData().getContractId());
    }
}
