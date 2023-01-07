package de.javsper.springboottradingibkr.client.service.position;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.PositionData;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import org.springframework.stereotype.Service;

@Service
class PoistionApiCaller implements ApiCallerWithoutParameter<PositionData> {

    private final EClientSocket client;

    public PoistionApiCaller(EClientSocket client) {
        this.client = client;
    }

    @Override
    public void callApi() {
        client.reqPositions();
    }
}
