package de.javsper.springboottradingdata.dao;

import de.javsper.springboottradingdata.ds.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeesDao extends CrudRepository<Employee, Integer> {
}
