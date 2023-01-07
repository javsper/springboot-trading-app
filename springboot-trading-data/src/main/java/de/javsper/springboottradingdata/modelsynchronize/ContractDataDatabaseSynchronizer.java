package de.javsper.springboottradingdata.modelsynchronize;

import com.ib.client.Contract;
import de.javsper.springboottradingdata.model.ComboLegData;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.IBKRContractToContractData;
import de.javsper.springboottradingdata.repository.ComboLegDataRepository;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import org.springframework.stereotype.Component;

import java.util.OptionalLong;

@Component
public class ContractDataDatabaseSynchronizer {

    private final IBKRContractToContractData ibkrContractToContractData;
    private final ContractDataRepository contractDataRepository;
    private final ComboLegDataRepository comboLegDataRepository;

    public ContractDataDatabaseSynchronizer(IBKRContractToContractData ibkrContractToContractData, ContractDataRepository contractDataRepository, ComboLegDataRepository comboLegDataRepository) {
        this.ibkrContractToContractData = ibkrContractToContractData;
        this.contractDataRepository = contractDataRepository;
        this.comboLegDataRepository = comboLegDataRepository;
    }

    public ContractData findInDBOrConvertAndSaveOrUpdateIfIdIsProvided(OptionalLong id, Contract contract){
        return contractDataRepository.findFirstByContractId(contract.conid())
                .orElseGet(()->{
            ContractData newContractData = ibkrContractToContractData.convertIBKRContract(contract);
            newContractData.getComboLegs().forEach(this::saveComboLegIfNotExistent);
            id.ifPresent(newContractData::setId);
            return contractDataRepository.save(newContractData);
        });


    }
    private void saveComboLegIfNotExistent(ComboLegData comboLegData){
        if(comboLegDataRepository.findFirstByContractIdAndActionAndRatioAndExchange(comboLegData.getContractId(),
                comboLegData.getAction(),
                comboLegData.getRatio(),
                comboLegData.getExchange()).isEmpty()){
            comboLegDataRepository.save(comboLegData);
        }
    }
}
