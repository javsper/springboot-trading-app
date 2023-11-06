package de.javsper.springboottradingweb.spxautotrade.service;

import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionChainData;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionMarketData;
import de.javsper.springboottradingdata.optionstradingservice.OptionTickerIdEncoder;
import de.javsper.springboottradingdata.optionstradingservice.OptionTickerIdResolver;
import de.javsper.springboottradingibkr.client.service.marketdata.StopMarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutoTradeChainDataStopLiveDataService {
    private final OptionTickerIdEncoder optionTickerIdEncoder;
    private final StopMarketDataService stopMarketDataService;

    public void stopMarketData(KafkaOptionChainData chain){
        chain.getCalls().getOptions().forEach((strike, data) ->{
            stopMarketDataService.stopMarketDataForTickerId(encodeId(data));
        });
        chain.getPuts().getOptions().forEach((strike, data) ->{
            stopMarketDataService.stopMarketDataForTickerId(encodeId(data));
        });

        }

    private int encodeId(KafkaOptionMarketData data) {
        OptionTickerIdResolver.OptionDetails detail =
                new OptionTickerIdResolver.OptionDetails(data.getLastTradeDate(), data.getSymbol(),
                        data.getStrike(), data.getRight());
        return  optionTickerIdEncoder.encodeOptionTickerId(detail);
    }
}

