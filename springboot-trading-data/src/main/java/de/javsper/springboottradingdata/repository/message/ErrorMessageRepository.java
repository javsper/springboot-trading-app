package de.javsper.springboottradingdata.repository.message;

import de.javsper.springboottradingdata.model.message.ErrorMessage;
import org.springframework.data.repository.CrudRepository;

public interface ErrorMessageRepository extends CrudRepository<ErrorMessage, Integer> {
}
