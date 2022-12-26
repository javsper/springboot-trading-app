package de.javsper.springboottradingdata.service;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.model.message.ErrorMessage;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.repository.message.ErrorMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ErrorMessageHandler {

    private final ErrorMessageRepository errorMessageRepository;

    public ErrorMessageHandler(List<IBKRDataTypeRepository<? extends IBKRDataTypeEntity>> repositories, ErrorMessageRepository errorMessageRepository) {
        this.errorMessageRepository = errorMessageRepository;
    }

    public void handleError(int id, String message) {
        log.warn(message);
        errorMessageRepository.save(ErrorMessage.builder().errorId(id).message(message).build());
    }

}