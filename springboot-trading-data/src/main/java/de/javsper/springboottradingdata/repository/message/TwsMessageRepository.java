package de.javsper.springboottradingdata.repository.message;

import de.javsper.springboottradingdata.model.message.TwsMessage;
import org.springframework.data.repository.CrudRepository;

public interface  TwsMessageRepository extends CrudRepository<TwsMessage, Integer> {
}
