package de.javsper.springboottradingibkr.client.service.historicalmarketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.service.IBKRTimeStampFormatter;
import de.javsper.springboottradingibkr.client.datamodel.HistoricalDataSettings;
import org.springframework.stereotype.Service;

@Service
public class HistoricalDataApiCaller {

    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final IBKRTimeStampFormatter ibkrTimeStampFormatter;
    private final PropertiesConfig propertiesConfig;

    public HistoricalDataApiCaller(EClientSocket client, ContractDataToIBKRContract contractDataToIBKRContract, IBKRTimeStampFormatter ibkrTimeStampFormatter, PropertiesConfig propertiesConfig) {
        this.client = client;
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.ibkrTimeStampFormatter = ibkrTimeStampFormatter;
        this.propertiesConfig = propertiesConfig;
    }

    public void callApi(ContractData contractData, HistoricalDataSettings settings) {
        propertiesConfig.addToActiveApiCalls((long) contractData.getContractId());
        client.reqHistoricalData(contractData.getContractId(),
                contractDataToIBKRContract.convertContractData(contractData),
                ibkrTimeStampFormatter.formatTimestampToDateAndTime(settings.getBackfillEndTime()),
                settings.getBackfillDuration(),
                settings.getBarSizeSetting(),
                settings.getWhatToShow().toString(),
                settings.isRegularTradingHours() ? 1 : 0,
                settings.getDateFormatStyle(),
                settings.isKeepUpToDate(),
                settings.getChartOptions());
    }
}
