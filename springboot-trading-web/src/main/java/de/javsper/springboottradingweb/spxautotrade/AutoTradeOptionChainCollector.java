package de.javsper.springboottradingweb.spxautotrade;

import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionChainData;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingibkr.client.service.marketdata.AutoTradeMarketDataService;
import de.javsper.springboottradingweb.spxautotrade.service.AutoTradeChainDataStopLiveDataService;
import de.javsper.springboottradingweb.spxautotrade.service.ChainDataContractDataCreateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoTradeOptionChainCollector {

  public static final int MININUM_CHAIN_SIZE = 10;
  private final LastTradeDateBuilder lastTradeDateBuilder;
  private final ChainDataContractDataCreateService chainDataContractDataCreateService;
  private final AutoTradeMarketDataService autoTradeMarketDataService;
  private final AutoTradeChainDataStopLiveDataService autoTradeChainDataStopLiveDataService;

  @Transactional
  @KafkaListener(
      groupId = "${kafka.consumer.auto.group.id}",
      topics = "${kafka.names.topic.streams.optionChainData}")
  public void processChainData(KafkaOptionChainData message) {
    if (message.getLastTradeDate().equals(lastTradeDateBuilder.getDateStringFromToday())
        && message.getSymbol().equals(Symbol.SPX)
        && message.getCalls().size() > MININUM_CHAIN_SIZE
        && message.getPuts().size() > MININUM_CHAIN_SIZE) {
      ContractDataDBO contractDataDBO =
          chainDataContractDataCreateService.createIronCondorContractData(message);
      autoTradeMarketDataService.requestLiveMarketDataForContractData(
          Integer.parseInt(contractDataDBO.getLastTradeDate()), contractDataDBO);
      log.info("Requested MarketData for: " + contractDataDBO.getComboLegsDescription());
      autoTradeChainDataStopLiveDataService.stopMarketData(message);
    }
  }
}
