package de.javsper.springboottradingibkr.client.service.accountsummary.profitandloss;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("AccountPnLCancelApiCaller")
@RequiredArgsConstructor
class AccountPnLCancelApiCaller implements ApiCallerWithoutParameter {

    private final EClientSocket client;
    private final PropertiesConfig propertiesConfig;

    @Override
    public void callApi() {
        client.cancelPnL(propertiesConfig.getPnlAccountId());
    }
}
