package de.javsper.springboottradingibkr.client.service.marketdata;

import com.ib.client.EClientSocket;
import com.ib.client.Types;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.optionstradingservice.OptionTickerIdEncoder;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("StartMarketDataApiCaller")
@RequiredArgsConstructor
class StartMarketDataApiCaller implements ApiCaller<ContractDataDBO> {

  private final EClientSocket client;
  private final ContractDataToIBKRContract contractDataToIBKRContract;
  private final PropertiesConfig propertiesConfig;
  private final OptionTickerIdEncoder optionTickerIdEncoder;

  /**
   * Options need a more specific tickerId to be used in Chain Data down the line
   * @param savedContract
   */
  public void callApi(ContractDataDBO savedContract) {
    int id =
        savedContract.getSecurityType().equals(Types.SecType.OPT)
            ? optionTickerIdEncoder.encodeOptionTickerId(savedContract)
            : savedContract.getId().intValue();
    client.reqMktData(
        id,
        contractDataToIBKRContract.convertContractData(savedContract),
        propertiesConfig.getGenericTicks(),
        false,
        false,
        null);
  }
}
