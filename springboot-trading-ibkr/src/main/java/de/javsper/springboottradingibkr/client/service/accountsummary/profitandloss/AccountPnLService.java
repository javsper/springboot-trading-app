package de.javsper.springboottradingibkr.client.service.accountsummary.profitandloss;

import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountPnLService {

    private final @Qualifier("AccountPnLApiCaller") ApiCallerWithoutParameter accountPnLApiCaller;
    private final  @Qualifier("AccountPnLCancelApiCaller") ApiCallerWithoutParameter accountPnLCancelApiCaller;

    public void getAccountPnL() {
        accountPnLApiCaller.callApi();
    }
    public void cancelAccountPnL(){
        accountPnLCancelApiCaller.callApi();
    }
}
