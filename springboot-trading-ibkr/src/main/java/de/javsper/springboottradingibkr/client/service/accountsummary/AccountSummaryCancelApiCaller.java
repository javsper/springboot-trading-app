package de.javsper.springboottradingibkr.client.service.accountsummary;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AccountSummaryCancelApiCaller")
@RequiredArgsConstructor
public class AccountSummaryCancelApiCaller implements ApiCallerWithoutParameter {

    private final EClientSocket client;
    private final PropertiesConfig propertiesConfig;


    @Override
    public void callApi() {
        client.cancelAccountSummary(propertiesConfig.getACCOUNT_SUMMARY_ID());
    }
}
