package de.javsper.springboottradingibkr.client.service.accountsummary.profitandloss;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.entity.ProfitAndLossData;
import de.javsper.springboottradingdata.repository.ConnectionDataRepository;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Qualifier("AccountPnLApiCaller")
public class AccountPnLApiCaller implements ApiCallerWithoutParameter<ProfitAndLossData> {

    private final EClientSocket client;
    private final ConnectionDataRepository connectionDataRepository;
    private final PropertiesConfig propertiesConfig;

    public AccountPnLApiCaller(EClientSocket client, ConnectionDataRepository connectionDataRepository,
                               PropertiesConfig propertiesConfig) {
        this.client = client;
        this.connectionDataRepository = connectionDataRepository;
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public void callApi() {
        connectionDataRepository.findById(propertiesConfig.getConnectionId()).ifPresent(
                (connectionData) -> {
            client.reqPnL(propertiesConfig.getPnlAccountId(), connectionData.getAccountList(), "");
        });
    }
}
