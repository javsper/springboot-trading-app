package de.javsper.springboottradingdata.repository.message;

import de.javsper.springboottradingdata.model.message.ErrorMessage;
import de.javsper.springboottradingdata.repository.BaseRepository;

import java.util.List;

public interface ErrorMessageRepository extends BaseRepository<ErrorMessage> {

    public List<ErrorMessage> findAllByMessageId(Integer errorId);
}
