package de.javsper.springboottradingdata.repository.message;

import de.javsper.springboottradingdata.model.entity.message.TwsMessage;
import org.springframework.data.repository.CrudRepository;

public interface  TwsMessageRepository extends CrudRepository<TwsMessage, Integer> {
}
