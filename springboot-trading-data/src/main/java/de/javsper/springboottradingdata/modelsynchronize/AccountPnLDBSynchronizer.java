package de.javsper.springboottradingdata.modelsynchronize;

import de.javsper.springboottradingdata.model.entity.ProfitAndLossData;
import de.javsper.springboottradingdata.repository.ProfitAndLossDataRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountPnLDBSynchronizer {

    private final ProfitAndLossDataRepository profitAndLossDataRepository;

    public AccountPnLDBSynchronizer(ProfitAndLossDataRepository profitAndLossDataRepository) {
        this.profitAndLossDataRepository = profitAndLossDataRepository;
    }

    public void saveToDB(ProfitAndLossData profitAndLossData){
        profitAndLossDataRepository.save(profitAndLossData);
    }
}
