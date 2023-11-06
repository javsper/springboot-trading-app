package de.javsper.springboottradingibkr.client.service.accountsummary.profitandloss;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.repository.ConnectionRepository;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service("AccountPnLApiCaller")
@RequiredArgsConstructor
class AccountPnLApiCaller implements ApiCallerWithoutParameter {

    private final EClientSocket client;
    private final ConnectionRepository connectionRepository;
    private final PropertiesConfig propertiesConfig;

    @Override
    public void callApi() {
        connectionRepository.findById(propertiesConfig.getConnectionId()).ifPresent(
                (connectionData) -> {
            client.reqPnL(propertiesConfig.getPnlAccountId(), connectionData.getAccountList(), "");
        });
    }
}
