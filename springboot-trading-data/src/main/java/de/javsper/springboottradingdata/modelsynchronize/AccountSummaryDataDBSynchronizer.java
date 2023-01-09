package de.javsper.springboottradingdata.modelsynchronize;

import de.javsper.springboottradingdata.model.entity.AccountSummaryData;
import de.javsper.springboottradingdata.repository.AccountSummaryDataRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountSummaryDataDBSynchronizer {

    private final AccountSummaryDataRepository accountSummaryDataRepository;

    public AccountSummaryDataDBSynchronizer(AccountSummaryDataRepository accountSummaryDataRepository) {
        this.accountSummaryDataRepository = accountSummaryDataRepository;
    }

    public void sendAccountSummaryMessage(AccountSummaryData accountSummaryData){
        accountSummaryData.setId(accountSummaryData.determineIdByTag());
        accountSummaryDataRepository.save(accountSummaryData);
    }

}
