package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.OrderData;
import org.springframework.data.repository.CrudRepository;

public interface OrderDataRepository  extends CrudRepository<OrderData, Integer> {
}
