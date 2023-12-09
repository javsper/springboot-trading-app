package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.entity.TradingHoursDbo;
import de.javsper.springboottradingdata.model.subtype.Symbol;

import java.util.Optional;

public interface TradingHoursRepository extends IBKRDataTypeRepository<TradingHoursDbo> {

  Optional<TradingHoursDbo> findBySymbol(Symbol symbol);
    }
