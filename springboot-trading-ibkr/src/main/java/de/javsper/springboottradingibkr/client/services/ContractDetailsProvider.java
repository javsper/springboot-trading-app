package de.javsper.springboottradingibkr.client.services;

import com.ib.client.Contract;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.IBKRContractToContractData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContractDetailsProvider {

    private final IBKRContractToContractData ibkrContractToContractData;
    private final ContractDataRepository contractDataRepository;

    public ContractDetailsProvider(IBKRContractToContractData ibkrContractToContractData,
                                   ContractDataRepository contractDataRepository) {
        this.ibkrContractToContractData = ibkrContractToContractData;
        this.contractDataRepository = contractDataRepository;
    }

    public void addContractDetailsFromAPIToContractData(int id, Contract contract) {
        if (contractDataRepository.findAllByContractId(contract.conid()).isEmpty()) {
            ContractData contractData = ibkrContractToContractData.convertIBKRContract(contract);
            contractData.setId(id);
            contractData.setTouchedByApi(true);
            contractDataRepository.save(contractData);
        } else {
            log.debug("Contract with Contract Id: " + contract.conid() + " already exists in DB");
            log.warn("Method should never be called in a Way this Code is executed!");
        }
    }
}
