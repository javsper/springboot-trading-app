package de.javsper.springboottradingdata.repository.message;

import de.javsper.springboottradingdata.model.data.message.ErrorMessage;
import de.javsper.springboottradingdata.repository.BaseRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ErrorMessageRepository extends BaseRepository<ErrorMessage> {

    public List<ErrorMessage> findAllByMessageId(Integer errorId);

    List<ErrorMessage> findAllByMessageIdAndCreateDateIsAfter(Integer errorId, Timestamp createDate);
}
