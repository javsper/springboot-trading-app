package de.javsper.springboottradingweb.controller.restapicontroller;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingweb.ConnectionInitiator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionInitiator connectionInitiator;
    private final PropertiesConfig propertiesConfig;

    @GetMapping("disconnect")
    public void disconnect(){
        connectionInitiator.disconnect();
    }

    @GetMapping("connect")
    public void connect(){
//        int port = stage.equals("live")? SpringbootTradingApplication.LIVE_TRADING_PORT:SpringbootTradingApplication.DOCKER_TRADING_PORT;
        connectionInitiator.connect(propertiesConfig.getTradingPort());
    }
}
