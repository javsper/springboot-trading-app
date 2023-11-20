package de.javsper.springboottradingweb.spxautotrade.service;

import de.javsper.springboottradingdata.model.data.kafka.OptionChainData;
import de.javsper.springboottradingdata.model.data.kafka.OptionMarketData;
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

  public void stopMarketData(OptionChainData chain) {
    chain
        .getCalls()
        .getOptions()
        .forEach(
            (strike, data) -> {
              stopMarketDataService.stopMarketDataForTickerId(encodeId(data));
            });
    chain
        .getPuts()
        .getOptions()
        .forEach(
            (strike, data) -> {
              stopMarketDataService.stopMarketDataForTickerId(encodeId(data));
            });
  }

  private int encodeId(OptionMarketData data) {
    OptionTickerIdResolver.OptionDetails detail =
        OptionTickerIdResolver.OptionDetails.builder()
            .date(data.getLastTradeDate())
            .symbol(data.getSymbol())
            .strike(data.getStrike())
            .right(data.getRight())
            .build();
    return optionTickerIdEncoder.encodeOptionTickerId(detail);
  }
}
