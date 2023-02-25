package de.javsper.springboottradingibkr.client.service.accountsummary;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AccountSummaryApiCaller")
@RequiredArgsConstructor
public class AccountSummaryApiCaller implements ApiCallerWithoutParameter {

    private final EClientSocket client;
    private final PropertiesConfig propertiesConfig;

    public final static String GROUD_NAME = "All";


    @Override
    public void callApi() {
        String tags =
                propertiesConfig.getACCRUED_CASH() + "," +
                        propertiesConfig.getBUYING_POWER() + "," +
                        propertiesConfig.getNET_LIQUIDATION();
        client.reqAccountSummary(propertiesConfig.getACCOUNT_SUMMARY_ID(), GROUD_NAME, tags);
    }
}
