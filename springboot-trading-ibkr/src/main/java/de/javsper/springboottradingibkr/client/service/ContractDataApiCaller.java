package de.javsper.springboottradingibkr.client.service;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.service.ApiResponseInEntityChecker;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContractDataApiCaller {

    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final ContractDataRepository contractDataRepository;
    private final ApiResponseInEntityChecker apiResponseInEntityChecker;

    public ContractDataApiCaller(EClientSocket client,
                                 ContractDataToIBKRContract contractDataToIBKRContract,
                                 ContractDataRepository contractDataRepository,
                                 ApiResponseInEntityChecker apiResponseInEntityChecker) {
        this.client = client;
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.contractDataRepository = contractDataRepository;
        this.apiResponseInEntityChecker = apiResponseInEntityChecker;
    }

    public Optional<ContractData> callContractDetailsFromAPI(ContractData contractData) {
        Contract ibkrContract = contractDataToIBKRContract.convertContractData(contractData);
        //ugly: having to increment by 2 because I am too stupid to do it properly
        Long nextId = getNextId(contractData);
        client.reqContractDetails(nextId.intValue(), ibkrContract);
        return getUpdatedContractData(nextId);
    }

    private Long getNextId(ContractData contractData) {
        return contractData.getId() != null ? contractData.getId() : (long) contractDataRepository.nextValidId() + 1;
    }


    private Optional<ContractData> getUpdatedContractData(Long id) {
        return this.apiResponseInEntityChecker.checkForApiResponseAndUpdate(contractDataRepository, id);
    }


}
