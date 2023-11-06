package de.javsper.springboottradingweb.spxautotrade;

import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionChainData;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.modelconverter.OptionChainDataToDBO;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoTradeOptionChainCollector {
  private final LastTradeDateBuilder lastTradeDateBuilder;
  private final OptionChainDataToDBO optionChainDataToDBO;

  @KafkaListener(
      groupId = "${kafka.consumer.auto.group.id}",
      topics = "${kafka.names.topic.streams.optionChainData}")
  public void processChainData(KafkaOptionChainData message) {
    if (message.getLastTradeDate().equals(lastTradeDateBuilder.getDateLongFromToday())
        && message.getSymbol().equals(Symbol.SPX)) {
      optionChainDataToDBO.convert(message);
    }
  }
}
