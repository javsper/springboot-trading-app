package de.javsper.springboottradingibkr.client.service.historicaldata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.modelconverter.ContractDboToIBKRContract;
import de.javsper.springboottradingdata.service.IBKRTimeStampFormatter;
import de.javsper.springboottradingdata.model.data.HistoricalDataSettings;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class HistoricalDataApiCaller implements ApiCaller<HistoricalDataSettings> {

    private final EClientSocket client;
    private final ContractDboToIBKRContract contractDboToIBKRContract;
    private final IBKRTimeStampFormatter ibkrTimeStampFormatter;

    public void callApi(HistoricalDataSettings settings) {
        client.reqHistoricalData(settings.getContractDBO().getContractId(),
                contractDboToIBKRContract.convertContractData(settings.getContractDBO()),
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
