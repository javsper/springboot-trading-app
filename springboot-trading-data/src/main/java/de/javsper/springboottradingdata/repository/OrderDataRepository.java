package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.entity.OrderDataDBO;

import java.util.Optional;

public interface OrderDataRepository  extends IBKRDataTypeRepository<OrderDataDBO> {

    Optional<OrderDataDBO> findTopByOrderByIdDesc();
}
