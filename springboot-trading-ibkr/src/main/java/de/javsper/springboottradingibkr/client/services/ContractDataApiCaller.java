package de.javsper.springboottradingibkr.client.services;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.service.ApiResponseInEntityChecker;
import org.springframework.stereotype.Component;

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

    public ContractData callContractDetailsFromAPI(ContractData contractData) {
        Contract ibkrContract = contractDataToIBKRContract.convertContractData(contractData);
        client.reqContractDetails(contractData.getId(), ibkrContract);
        return getUpdatedContractData(contractData.getId());
    }

    private ContractData getUpdatedContractData(Integer id) {
        return this.apiResponseInEntityChecker.checkForApiResponseAndUpdate(contractDataRepository,id);
    }


}
