package de.javsper.springboottradingibkr.client.service.position.profitandloss;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ConnectionDbo;
import de.javsper.springboottradingdata.repository.ConnectionRepository;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("SinglePnLApiCaller")
@RequiredArgsConstructor
class SinglePnLApiCaller implements ApiCallerWithId {

    private final EClientSocket client;
    private final ConnectionRepository connectionRepository;
    private final PropertiesConfig propertiesConfig;

    @Override
    public void callApi(int id) {
        ConnectionDbo connectionDBO =
                connectionRepository.findById(propertiesConfig.getConnectionId()).orElseThrow();
        client.reqPnLSingle(id, connectionDBO.getAccountList(), "", id);
    }
}
