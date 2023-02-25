package de.javsper.springboottradingibkr.client.service.position.profitandloss;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ConnectionData;
import de.javsper.springboottradingdata.repository.ConnectionDataRepository;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("SinglePnLApiCaller")
@RequiredArgsConstructor
class SinglePnLApiCaller implements ApiCallerWithId {

    private final EClientSocket client;
    private final ConnectionDataRepository connectionDataRepository;
    private final PropertiesConfig propertiesConfig;

    @Override
    public void callApi(int id) {
        ConnectionData connectionData =
                connectionDataRepository.findById(propertiesConfig.getConnectionId()).orElseThrow();
        client.reqPnLSingle(id, connectionData.getAccountList(), "", id);
    }
}
