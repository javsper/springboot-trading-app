package de.javsper.springboottradingibkr.client.services;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Component;

@Component
public class ContractDataApiCaller {

    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final ContractDataRepository contractDataRepository;
    private final RepositoryRefreshService repositoryRefreshService;

    public ContractDataApiCaller(EClientSocket client, ContractDataToIBKRContract contractDataToIBKRContract, ContractDataRepository contractDataRepository, RepositoryRefreshService repositoryRefreshService) {
        this.client = client;
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.contractDataRepository = contractDataRepository;
        this.repositoryRefreshService = repositoryRefreshService;
    }

    public ContractData callContractDetailsFromAPI(ContractData contractData) {
        Contract ibkrContract = contractDataToIBKRContract.convertContractData(contractData);
        client.reqContractDetails(contractData.getId(), ibkrContract);
        return getUpdatedContractData(contractData.getId());
    }

    private ContractData getUpdatedContractData(Integer id) {
        ContractData savedContactData;
        int i = 0;
        do {
            i++;
        repositoryRefreshService.clearCacheAndWait(contractDataRepository);
        savedContactData = contractDataRepository.findById(id).orElseThrow();
        System.out.print(i +",");
        }while(!savedContactData.isTouchedByApi());
        return savedContactData;
    }


}
