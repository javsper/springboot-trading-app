package de.javsper.springboottradingweb.spxautotrade;

import de.javsper.springboottradingdata.model.data.StandardMarketData;
import de.javsper.springboottradingibkr.client.service.marketdata.StopMarketDataService;
import de.javsper.springboottradingweb.spxautotrade.scheduler.IronCondorScheduler;
import de.javsper.springboottradingweb.spxautotrade.service.AutoTradeCallAndPutDataRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class MarketDataAutoTradeKafkaHandler {



    private final AutoTradeCallAndPutDataRequestService autoTradeOptionDataService;
    private final StopMarketDataService stopMarketDataService;

    @KafkaListener(groupId = "${kafka.consumer.auto.group.id}", topics = "${kafka.names.topic.standardMarketData}")
    public void consumeSummaryMessage(StandardMarketData message) {
        if (message.getTickerId() == IronCondorScheduler.SPX_TICKER_ID) {
            log.info("Tick: " + message.getTickerId() + message.getField());
            double price = message.getPrice();
            autoTradeOptionDataService.getOptionContractsAndCallAPI(price);
            stopMarketDataService.stopMarketDataForContractId(IronCondorScheduler.SPX_TICKER_ID);
        }
    }
}
