package de.javsper.springboottradingibkr.client.service.contract;

import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.service.apiresponsecheck.ContractDataApiResponseChecker;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class ContractDataCallAndResponseHandler {

    private final ContractDataRepository contractDataRepository;
    private final ContractDataApiResponseChecker contractDataApiResponseChecker;
    private final ContractDataApiCaller contractDataApiCaller;

    public ContractDataCallAndResponseHandler(ContractDataRepository contractDataRepository, ContractDataApiResponseChecker contractDataApiResponseChecker, ContractDataApiCaller contractDataApiCaller) {
        this.contractDataRepository = contractDataRepository;
        this.contractDataApiResponseChecker = contractDataApiResponseChecker;
        this.contractDataApiCaller = contractDataApiCaller;
    }

    public Optional<ContractData> callContractDetailsFromAPI(ContractData contractData) {
        //ugly: having to increment by 2 because I am too stupid to do it properly
        int nextId = getNextId(contractData);
        contractDataApiCaller.callApi(nextId, contractData);
        return contractDataApiResponseChecker.checkForApiResponseAndUpdate(nextId);
    }



    private int getNextId(ContractData contractData) {
        return contractData.getId() != null ? contractData.getId().intValue() : contractDataRepository.nextValidId() + 1;
    }
}
