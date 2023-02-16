package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.entity.database.OrderData;

import java.util.Optional;

public interface OrderDataRepository  extends IBKRDataTypeRepository<OrderData> {

    Optional<OrderData> findTopByOrderByIdDesc();
}
