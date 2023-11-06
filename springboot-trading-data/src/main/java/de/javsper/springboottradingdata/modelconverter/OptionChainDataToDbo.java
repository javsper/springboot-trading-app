package de.javsper.springboottradingdata.modelconverter;

import de.javsper.springboottradingdata.model.data.entity.OptionChainDbo;
import de.javsper.springboottradingdata.model.data.entity.OptionListDbo;
import de.javsper.springboottradingdata.model.data.entity.OptionMarketDataDbo;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionChainData;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionMarketData;
import de.javsper.springboottradingdata.repository.OptionChainRepository;
import de.javsper.springboottradingdata.repository.OptionListRepository;
import de.javsper.springboottradingdata.repository.OptionMarketDataRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptionChainDataToDbo {

  public static final int NEGATIVE_FOR_PUTS = -1;
  private final OptionMarketDataRepository optionMarketDataRepository;
  private final OptionListRepository optionListRepository;
  private final OptionChainRepository optionChainRepository;

  @Transactional
  public OptionChainDbo convert(KafkaOptionChainData kafkaOptionChainData) {
    List<OptionMarketDataDbo> callList = mapToList(kafkaOptionChainData.getCalls().getOptions());
    List<OptionMarketDataDbo> putList = mapToList(kafkaOptionChainData.getPuts().getOptions());
    OptionListDbo callsDBO =
        optionListRepository.save(
            OptionListDbo.builder()
                .id(kafkaOptionChainData.getLastTradeDate())
                .optionList(callList)
                .build());
    OptionListDbo putsDBO =
        optionListRepository.save(
            OptionListDbo.builder()
                .id(kafkaOptionChainData.getLastTradeDate() * -1)
                .optionList(putList)
                .build());
    return optionChainRepository.save(
        OptionChainDbo.builder()
            .lastTradeDate(kafkaOptionChainData.getLastTradeDate())
            .symbol(kafkaOptionChainData.getSymbol())
            .calls(callsDBO)
            .puts(putsDBO)
            .build());
  }

  private List<OptionMarketDataDbo> mapToList(Map<Double, KafkaOptionMarketData> map) {
    List<OptionMarketDataDbo> list = new ArrayList<>();
    map.forEach(
        (key, value) -> {
          list.add(toOptionMarketDataDBO(value));
        });
    return list;
  }

  private OptionMarketDataDbo toOptionMarketDataDBO(KafkaOptionMarketData optionMarketData) {
    return optionMarketDataRepository.save(
        OptionMarketDataDbo.builder()
            .tickerId((long) optionMarketData.getTickerId())
            .strike(optionMarketData.getStrike())
            .right(optionMarketData.getRight())
            .symbol(optionMarketData.getSymbol())
            .lastTradeDate(optionMarketData.getLastTradeDate())
            .field(optionMarketData.getField())
            .tickAttrib(optionMarketData.getTickAttrib())
            .impliedVol(optionMarketData.getImpliedVol())
            .delta(optionMarketData.getDelta())
            .optPrice(optionMarketData.getOptPrice())
            .pvDividend(optionMarketData.getPvDividend())
            .gamma(optionMarketData.getGamma())
            .vega(optionMarketData.getVega())
            .theta(optionMarketData.getTheta())
            .undPrice(optionMarketData.getUndPrice())
            .build());
  }
}
