package de.javsper.springboottradingdata.kafkastreams;

import com.ib.client.Types;
import de.javsper.springboottradingdata.constants.AutoDayTradeConstants;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionChainData;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionListData;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionMarketData;
import de.javsper.springboottradingdata.optionstradingservice.OptionTickerIdResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreamOptionChainDataCreator {

  private final OptionTickerIdResolver optionTickerIdResolver;

  public KafkaOptionChainData buildChain(KafkaOptionMarketData marketData, KafkaOptionChainData aggregatedChain) {
    if (aggregatedChain.getLastTradeDate() == null) {
      aggregatedChain = createNewChain(marketData);
    }

    if (marketData.getField().equals(AutoDayTradeConstants.CHAIN_SAVE_FIELD)) {
      if (marketData.getRight().equals(Types.Right.Call)) {
        aggregatedChain.getCalls().put(marketData.getStrike(), marketData);
      } else if (marketData.getRight().equals(Types.Right.Put)) {
        aggregatedChain.getPuts().put(marketData.getStrike(), marketData);
      }
    }

    return aggregatedChain;
  }

  private KafkaOptionChainData createNewChain(KafkaOptionMarketData marketData) {
    return KafkaOptionChainData.builder()
        .lastTradeDate(marketData.getLastTradeDate())
        .symbol(marketData.getSymbol())
        .puts(new KafkaOptionListData())
        .calls(new KafkaOptionListData())
        .build();
  }
}
