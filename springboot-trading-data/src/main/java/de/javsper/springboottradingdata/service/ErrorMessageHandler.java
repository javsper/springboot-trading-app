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

    private final List<IBKRDataTypeRepository<? extends IBKRDataTypeEntity>> repositories;
    private final ErrorMessageRepository errorMessageRepository;

    public ErrorMessageHandler(List<IBKRDataTypeRepository<? extends IBKRDataTypeEntity>> repositories, ErrorMessageRepository errorMessageRepository) {
        this.repositories = repositories;
        this.errorMessageRepository = errorMessageRepository;
    }

    public void handleError(int id, String message) {
        log.warn(message);
        errorMessageRepository.save(ErrorMessage.builder().id(id).message(message).build());

        //Loops over all IBKRDataTypeRepositories and checks if the id of error is present, if so updates the Object.
        repositories.forEach((ibkrDataTypeRepository) -> {
            updateRepository(ibkrDataTypeRepository, id);
        });
    }
    private <T extends IBKRDataTypeEntity> void updateRepository(IBKRDataTypeRepository<T> repository, Integer id){
        repository.findById(id).ifPresent((data -> {
            data.setTouchedByApi(true);
            repository.save(data);
        }));
    }
}