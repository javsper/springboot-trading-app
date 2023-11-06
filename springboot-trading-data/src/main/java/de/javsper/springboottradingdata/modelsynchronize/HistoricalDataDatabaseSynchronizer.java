package de.javsper.springboottradingdata.modelsynchronize;

import com.ib.client.Bar;
import de.javsper.springboottradingdata.model.data.entity.HistoricalDbo;
import de.javsper.springboottradingdata.modelconverter.BarToHistoricalDataDbo;
import de.javsper.springboottradingdata.repository.HistoricalRepository;
import de.javsper.springboottradingdata.service.IBKRTimeStampFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoricalDataDatabaseSynchronizer {

    private final HistoricalRepository historicalRepository;
    private final BarToHistoricalDataDbo barToHistoricalDataDbo;
    private final IBKRTimeStampFormatter ibkrTimeStampFormatter;

    public HistoricalDbo findInDbOrSave(int id, Bar bar) {
        return historicalRepository.findFirstByContractIdAndTimeAndCount(id, ibkrTimeStampFormatter.formatStringToTimeStamp(bar.time()), bar.count())
                .orElseGet(() -> {
                    HistoricalDbo newHistoricalDbo = barToHistoricalDataDbo.convert(bar);
                    newHistoricalDbo.setContractId(id);
                    return historicalRepository.save(newHistoricalDbo);
                });
    }
}
