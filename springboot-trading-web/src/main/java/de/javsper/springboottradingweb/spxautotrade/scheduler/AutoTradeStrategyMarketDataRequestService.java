package de.javsper.springboottradingweb.spxautotrade.scheduler;

import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.model.data.entity.OptionChainDataDBO;
import de.javsper.springboottradingdata.model.data.kafka.KafkaOptionChainData;
import de.javsper.springboottradingdata.modelconverter.DBOToOptionChainData;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingdata.repository.OptionChainDataRepository;
import de.javsper.springboottradingibkr.client.service.marketdata.AutoTradeMarketDataService;
import de.javsper.springboottradingweb.spxautotrade.service.AutoTradeChainDataStopLiveDataService;
import de.javsper.springboottradingweb.spxautotrade.service.ChainDataContractDataCreateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AutoTradeStrategyMarketDataRequestService {

  private final LastTradeDateBuilder lastTradeDateBuilder;
  private final ChainDataContractDataCreateService chainDataContractDataCreateService;
  private final AutoTradeMarketDataService autoTradeMarketDataService;
  private final AutoTradeChainDataStopLiveDataService autoTradeChainDataStopLiveDataService;
  private final OptionChainDataRepository optionChainDataRepository;
  private final DBOToOptionChainData dboToOptionChainData;


  @Transactional
  public void createStrategyFromOptionChain() {
    KafkaOptionChainData chainData = dboToOptionChainData.toOptionChainData(findFromRepo());

    ContractDataDBO contractDataDBO =
        chainDataContractDataCreateService.createIronCondorContractData(chainData);
    autoTradeMarketDataService.requestLiveMarketDataForContractData(
        Integer.parseInt(contractDataDBO.getLastTradeDate()), contractDataDBO);
    log.info("Requested MarketData for: " + contractDataDBO.getComboLegsDescription());
    autoTradeChainDataStopLiveDataService.stopMarketData(chainData);
  }

  private OptionChainDataDBO findFromRepo() {
    return optionChainDataRepository
        .findById(lastTradeDateBuilder.getDateLongFromToday())
        .orElseGet(
            () -> {
              try {
                Thread.sleep(20L);
              } catch (InterruptedException e) {
                log.error("Thread.sleep() failed", e);
              }
              return findFromRepo();
            });
  }
}
