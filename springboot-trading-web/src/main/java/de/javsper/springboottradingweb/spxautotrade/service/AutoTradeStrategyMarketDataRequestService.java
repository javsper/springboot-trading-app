package de.javsper.springboottradingweb.spxautotrade.service;

import de.javsper.springboottradingdata.config.TradeRuleSettingsConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.model.data.entity.OptionChainDbo;
import de.javsper.springboottradingdata.model.data.kafka.OptionChainData;
import de.javsper.springboottradingdata.model.subtype.Strategy;
import de.javsper.springboottradingdata.modelconverter.DboToOptionChainData;
import de.javsper.springboottradingdata.optionstradingservice.AutotradeDbAndTickerIdEncoder;
import de.javsper.springboottradingdata.repository.OptionChainRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import de.javsper.springboottradingibkr.client.service.marketdata.AutoTradeMarketDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AutoTradeStrategyMarketDataRequestService {

  private final TradeRuleSettingsConfig tradeRuleSettingsConfig;
  private final StrategyFromChainDataCreator strategyFromChainDataCreator;
  private final AutoTradeMarketDataService autoTradeMarketDataService;
  private final AutoTradeChainDataStopLiveDataService autoTradeChainDataStopLiveDataService;
  private final OptionChainRepository optionChainRepository;
  private final DboToOptionChainData dboToOptionChainData;
  private final RepositoryRefreshService repositoryRefreshService;
  private final AutotradeDbAndTickerIdEncoder autotradeDbAndTickerIdEncoder;

  @Transactional
  public void createStrategyFromOptionChain(Strategy strategy) {
    OptionChainData chainData = dboToOptionChainData.toOptionChainData(findFromRepo());

    ContractDbo contractDBO = strategyFromChainDataCreator.createIronCondorContractData(chainData);
    autoTradeMarketDataService.requestLiveMarketDataForContractData(
        createIdForContractWithIronCondor(contractDBO, strategy), contractDBO);
    log.info("Requested MarketData for: " + contractDBO.getComboLegsDescription());
    autoTradeChainDataStopLiveDataService.stopMarketData(chainData);
  }

  private OptionChainDbo findFromRepo() {
    return optionChainRepository
        .findById(
            autotradeDbAndTickerIdEncoder.generateLongIdIdForTodayBySymbol(
                tradeRuleSettingsConfig.getTradeSymbol()))
        .orElseGet(
            () -> {
              repositoryRefreshService.clearCacheAndWait(optionChainRepository);
              return findFromRepo();
            });
  }

  private int createIdForContractWithIronCondor(ContractDbo contractDBO, Strategy strategy) {
    return autotradeDbAndTickerIdEncoder.generateIntForLastTradeDateBySymbolAndStrategy(
        Long.valueOf(contractDBO.getLastTradeDate()),
        contractDBO.getSymbol(),
        strategy);
  }
}
