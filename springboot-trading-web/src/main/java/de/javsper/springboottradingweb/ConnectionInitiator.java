package de.javsper.springboottradingweb;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ConnectionDataDBO;
import de.javsper.springboottradingdata.model.data.message.TwsMessage;
import de.javsper.springboottradingdata.repository.ConnectionDataRepository;
import de.javsper.springboottradingibkr.client.service.EReaderHolder;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionInitiator {


    private final Integer LAST_CLIENT_ID = 0;
    private final EClientSocket client;
    private final ConnectionDataRepository connectionDataRepository;
    private final EReaderHolder eReaderHolder;
    private final PropertiesConfig propertiesConfig;

    public void connect(int port) {
        if (client.isConnected()) {
            return;
        }
        client.eConnect("", port, 0);

        if (client.isConnected()) {
            ConnectionDataDBO connectionDataDBO = ConnectionDataDBO.builder()
                    .id(propertiesConfig.getConnectionId())
                    .ipAddress("")
                    .port(port)
                    .clientId(0)
                    .optCapts("")
                    .isFAAccount(false)
                    .disconnectInProgress(false)
                    .connected(true)
                    .build();

            TwsMessage msg = TwsMessage.builder()
                    .message("Connected to Tws server version " +
                            client.serverVersion() + " at " +
                            client.getTwsConnectionTime()).build();
            log.info(msg.getMessage());
            connectionDataRepository.save(connectionDataDBO);
            eReaderHolder.startReader();
        }else{
            throw new RuntimeException("Connection to Tws Failed!!");
        }
    }

    @PreDestroy
    public ConnectionDataDBO disconnect(){
        ConnectionDataDBO connectionDataDBO =
                connectionDataRepository.findById(propertiesConfig.getConnectionId()).orElse(ConnectionDataDBO.builder().build());
        connectionDataDBO.setDisconnectInProgress(true);
        ConnectionDataDBO processingConnectionDataDBO = connectionDataRepository.save(connectionDataDBO);

        client.eDisconnect();

        processingConnectionDataDBO.setConnected(false);
        processingConnectionDataDBO.setDisconnectInProgress(false);
        return connectionDataRepository.save(processingConnectionDataDBO);

    }
}
