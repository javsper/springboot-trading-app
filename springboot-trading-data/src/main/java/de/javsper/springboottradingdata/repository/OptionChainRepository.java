package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.entity.OptionChainDbo;
import de.javsper.springboottradingdata.model.subtype.Symbol;

public interface OptionChainRepository extends IBKRDataTypeRepository<OptionChainDbo>{

    void deleteBySymbolAndLastTradeDate(Symbol symbol, Long lastTradeDate);
}
