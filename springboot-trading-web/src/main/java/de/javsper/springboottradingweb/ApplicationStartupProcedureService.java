package de.javsper.springboottradingweb;

import com.ib.client.Types;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.dataobject.ContractDataTemplates;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.repository.ContractRepository;
import de.javsper.springboottradingibkr.client.service.contract.ContractDataCallAndResponseHandler;
import de.javsper.springboottradingibkr.client.service.position.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationStartupProcedureService {

  private final PropertiesConfig propertiesConfig;
  private final ConnectionInitiator connectionInitiator;
  private final PositionService positionService;
  private final ContractDataCallAndResponseHandler contractDataCallAndResponseHandler;
  private final ContractRepository contractRepository;

  @Value("${app.startup.connect-tws:true}")
  private boolean connectTws;

  public void onStartUp() {
    if (!connectTws) {
      log.warn("Skipping IBKR TWS startup (app.startup.connect-tws=false).");
      return;
    }

    connectionInitiator.connect(propertiesConfig.getTradingPort());
    try {
      // stupid shit because the API needs time or it will close the connection in the next step
      Thread.sleep(500L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    positionService.callPortfolio();
    if (contractRepository
        .findAllBySecurityTypeAndSymbol(Types.SecType.IND, Symbol.SPX)
        .isEmpty()) {
      contractDataCallAndResponseHandler.callContractDetailsFromAPI(
          ContractDataTemplates.SpxData());
    }
  }
}
