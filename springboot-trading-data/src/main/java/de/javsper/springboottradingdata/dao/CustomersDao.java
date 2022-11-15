package de.javsper.springboottradingdata.dao;

import de.javsper.springboottradingdata.ds.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomersDao extends CrudRepository<Customer, Integer> {
}
