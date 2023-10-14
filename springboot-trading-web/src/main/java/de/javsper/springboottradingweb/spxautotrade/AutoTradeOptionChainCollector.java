package de.javsper.springboottradingweb.spxautotrade;

import de.javsper.springboottradingdata.model.data.OptionChainData;
import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingibkr.client.service.marketdata.AutoTradeMarketDataService;
import de.javsper.springboottradingweb.spxautotrade.service.ChainDataContractDataCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoTradeOptionChainCollector {

  private final LastTradeDateBuilder lastTradeDateBuilder;
  private final ChainDataContractDataCreateService chainDataContractDataCreateService;
  private final AutoTradeMarketDataService autoTradeMarketDataService;

  @KafkaListener(
      groupId = "${kafka.consumer.auto.group.id}",
      topics = "${kafka.names.topic.streams.optionChainData}")
  public void processChainData(OptionChainData message) {
    if (message.getLastTradeDate().equals(lastTradeDateBuilder.getDateStringFromToday())
        && message.getSymbol().equals(Symbol.SPX.name())) {
      ContractData contractData =
          chainDataContractDataCreateService.createIronCondorContractData(message);
      autoTradeMarketDataService.requestLiveMarketDataForContractData(
          Integer.parseInt(contractData.getLastTradeDate()), contractData);
      log.info("Requested MarketData for: " + contractData.getComboLegsDescription());
    }
  }
}
