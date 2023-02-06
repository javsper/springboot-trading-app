package de.javsper.springboottradingibkr.client.service.accountsummary.profitandloss;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.entity.ProfitAndLossData;
import de.javsper.springboottradingdata.service.apiresponsecheck.AccountPnLApiResponseChecker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountPnLService {

    private final AccountPnLApiCaller accountPnLApiCaller;
    private final AccountPnLCancelApiCaller accountPnLCancelApiCaller;
    private final AccountPnLApiResponseChecker accountPnLApiResponseChecker;
    private final PropertiesConfig propertiesConfig;

    public AccountPnLService(AccountPnLApiCaller accountPnLApiCaller, AccountPnLCancelApiCaller accountPnLCancelApiCaller, AccountPnLApiResponseChecker accountPnLApiResponseChecker, PropertiesConfig propertiesConfig) {
        this.accountPnLApiCaller = accountPnLApiCaller;
        this.accountPnLCancelApiCaller = accountPnLCancelApiCaller;
        this.accountPnLApiResponseChecker = accountPnLApiResponseChecker;
        this.propertiesConfig = propertiesConfig;
    }

    public Optional<ProfitAndLossData> getAccountPnL(){
        accountPnLCancelApiCaller.callApi();
        accountPnLApiCaller.callApi();
        return accountPnLApiResponseChecker.checkForApiResponseAndUpdate(propertiesConfig.getPnlAccountId());
    }
}
