package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.entity.OrderDbo;

import java.util.Optional;

public interface OrderRepository extends IBKRDataTypeRepository<OrderDbo> {

    Optional<OrderDbo> findTopByOrderByIdDesc();
}
