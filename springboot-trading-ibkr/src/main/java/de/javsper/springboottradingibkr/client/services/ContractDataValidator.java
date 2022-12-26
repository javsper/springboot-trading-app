package de.javsper.springboottradingibkr.client.services;

import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.OrderData;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractDataValidator {

    private final UniqueContractDataProvider uniqueContractDataProvider;

    public ContractDataValidator(UniqueContractDataProvider uniqueContractDataProvider) {
        this.uniqueContractDataProvider = uniqueContractDataProvider;
    }

    public boolean validate(OrderData orderData) {

        Optional<ContractData> contractDataOpt = uniqueContractDataProvider.getExistingContractDataOrCallApi(orderData.getContractData());

        if (contractDataOpt.isPresent()) {
            orderData.setContractData(contractDataOpt.get());
            return true;
        }else{
            return false;
        }

    }
}
