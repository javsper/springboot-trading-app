package de.javsper.springboottradingweb.spxautotrade.service;

import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingdata.repository.OptionChainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AutoTradeStaleOptionChainDataClearService {

    private final OptionChainRepository optionChainRepository;
    private final LastTradeDateBuilder lastTradeDateBuilder;


    @Transactional
    public void clearStaleOptionMarketData() {
        //clear Db of possible stale values
        optionChainRepository.deleteBySymbolAndLastTradeDate(
                Symbol.SPX, lastTradeDateBuilder.getDateLongFromToday());

    }
}
