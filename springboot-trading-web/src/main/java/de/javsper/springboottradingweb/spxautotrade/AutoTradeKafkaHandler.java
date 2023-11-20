package de.javsper.springboottradingweb.spxautotrade;

import de.javsper.springboottradingdata.model.data.kafka.OptionChainData;
import de.javsper.springboottradingdata.model.data.kafka.StandardMarketData;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.modelconverter.OptionChainDataToDbo;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingdata.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoTradeKafkaHandler {
  private final LastTradeDateBuilder lastTradeDateBuilder;
  private final OptionChainDataToDbo optionChainDataToDBO;
  private final PositionRepository positionRepository;

  @KafkaListener(
      groupId = "${kafka.consumer.auto.group.id}",
      topics = "${kafka.names.topic.streams.optionChainData}")
  public void processChainData(OptionChainData message) {
    if (message.getLastTradeDate().equals(lastTradeDateBuilder.getDateLongFromToday())
        && message.getSymbol().equals(Symbol.SPX)) {
      optionChainDataToDBO.convertAndSave(message);
    }
  }
  @KafkaListener(
          groupId = "${kafka.consumer.auto.group.id}",
          topics = "${kafka.names.topic.standardMarketData}"
  )
  public void processLiveMarketData(StandardMarketData message) {
    if(message.getTickerId()== lastTradeDateBuilder.getDateIntFromToday()){
      positionRepository.findById(lastTradeDateBuilder.getDateLongFromToday()).ifPresent((position)->
      {

      });
    }
  }
}
