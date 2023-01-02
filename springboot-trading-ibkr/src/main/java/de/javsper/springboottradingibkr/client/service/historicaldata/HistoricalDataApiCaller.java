package de.javsper.springboottradingibkr.client.service.historicaldata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.service.IBKRTimeStampFormatter;
import de.javsper.springboottradingdata.model.HistoricalDataSettings;
import org.springframework.stereotype.Service;

@Service
class HistoricalDataApiCaller {

    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final IBKRTimeStampFormatter ibkrTimeStampFormatter;

    public HistoricalDataApiCaller(EClientSocket client, ContractDataToIBKRContract contractDataToIBKRContract, IBKRTimeStampFormatter ibkrTimeStampFormatter) {
        this.client = client;
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.ibkrTimeStampFormatter = ibkrTimeStampFormatter;
    }

    public void callApi(HistoricalDataSettings settings) {
        client.reqHistoricalData(settings.getContractData().getContractId(),
                contractDataToIBKRContract.convertContractData(settings.getContractData()),
                ibkrTimeStampFormatter.formatTimestampToDateAndTime(settings.getBackfillEndTime()),
                settings.getBackfillDuration(),
                settings.getBarSizeSetting().getValue(),
                settings.getWhatToShow().toString(),
                settings.isRegularTradingHours() ? 1 : 0,
                settings.getDateFormatStyle(),
                settings.isKeepUpToDate(),
                settings.getChartOptions());
    }
}
