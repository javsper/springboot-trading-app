package de.javsper.springboottradingdata.repository.message;

import de.javsper.springboottradingdata.model.message.TickerMessage;
import org.springframework.data.repository.CrudRepository;

public interface TickerMessageRepository extends CrudRepository<TickerMessage, Integer> {
}
