package de.javsper.springboottradingibkr.client.responsehandler;

import de.javsper.springboottradingdata.config.TradeRuleSettingsConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.model.data.entity.PositionDbo;
import de.javsper.springboottradingdata.model.data.kafka.PositionData;
import de.javsper.springboottradingdata.modelconverter.PositionDataToDbo;
import de.javsper.springboottradingdata.modelsynchronize.PositionDataDatabaseSynchronizer;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamsAggregatedPositionHandler {

  private final UniqueContractDataProvider uniqueContractDataProvider;
  private final PositionDataDatabaseSynchronizer positionDataDatabaseSynchronizer;
  private final PositionDataToDbo positionDataToDbo;
  private final LastTradeDateBuilder lastTradeDateBuilder;
  private final TradeRuleSettingsConfig tradeRuleSettingsConfig;

  public PositionData persistContractAndPositionData(PositionData positionData) {
    PositionDbo positionDbo = positionDataToDbo.convert(positionData);
    ContractDbo persistedContract =
        uniqueContractDataProvider
            .getExistingContractDataOrCallApi(positionDbo.getContractDBO())
            .orElseThrow();
    positionDbo.setContractDBO(persistedContract);
    return positionDataDatabaseSynchronizer.updateInDbOrSave(positionDbo).toKafkaPositionData();
  }

  /**
   * if LastTradeDate is today and Symbol is SPX it is Auto Trade
   */
  private void setIdIfAutoTrade(ContractDbo contractDbo, PositionDbo positionDbo) {
    if (contractDbo.getLastTradeDate().equals(lastTradeDateBuilder.getDateStringFromToday())
        && contractDbo.getSymbol().equals(tradeRuleSettingsConfig.getTradeSymbol())) {
      positionDbo.setId(lastTradeDateBuilder.getDateLongFromToday());
    }
  }
}
