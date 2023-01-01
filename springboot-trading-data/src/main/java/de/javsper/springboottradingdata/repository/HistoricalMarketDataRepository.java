package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.HistoricalMarketData;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface HistoricalMarketDataRepository extends IBKRDataTypeRepository<HistoricalMarketData> {

List<HistoricalMarketData> findAllByContractId(Integer id);

Optional<HistoricalMarketData>findFirstByContractIdAndTime(Integer id, Timestamp time);
}
