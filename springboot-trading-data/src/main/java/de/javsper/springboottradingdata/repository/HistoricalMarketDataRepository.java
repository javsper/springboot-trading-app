package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.HistoricalMarketData;

import java.util.List;

public interface HistoricalMarketDataRepository extends IBKRDataTypeRepository<HistoricalMarketData> {

List<HistoricalMarketData> findAllByGroupId(Integer id);
}
