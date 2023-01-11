package de.javsper.springboottradingweb.controller.restapicontroller;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.repository.ConnectionDataRepository;
import de.javsper.springboottradingweb.ConnectionInitiator;
import de.javsper.springboottradingweb.SpringbootTradingApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController {

    private final ConnectionInitiator connectionInitiator;
    private final EClientSocket client;
    private final ConnectionDataRepository connectionDataRepository;

    public ConnectionController(ConnectionInitiator connectionInitiator, EClientSocket client, ConnectionDataRepository connectionDataRepository) {
        this.connectionInitiator = connectionInitiator;
        this.client = client;
        this.connectionDataRepository = connectionDataRepository;
    }

    @GetMapping("disconnect")
    public ResponseEntity<Boolean> disconnect(){
        client.eDisconnect();;
        connectionDataRepository.deleteAll();
        return ResponseEntity.ok(false);
    }

    @GetMapping("connect")
    public ResponseEntity<Boolean> connect(@RequestParam(defaultValue = "paper", name = "stage") String stage){
        int port = stage.equals("live")? SpringbootTradingApplication.LIVE_TRADING_PORT:SpringbootTradingApplication.PAPER_TRADING_PORT;
        return ResponseEntity.ok(connectionInitiator.connect(port));
    }
}
