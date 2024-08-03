package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.entity.OptionMarketDataDbo;
import de.javsper.springboottradingdata.model.subtype.Symbol;

public interface OptionMarketDataRepository extends IBKRDataTypeRepository<OptionMarketDataDbo>{

    void deleteBySymbolAndLastTradeDate(Symbol symbol, String lastTradeDate);
}
