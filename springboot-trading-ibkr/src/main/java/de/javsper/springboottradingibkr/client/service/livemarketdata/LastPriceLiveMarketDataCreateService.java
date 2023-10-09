package de.javsper.springboottradingibkr.client.service.livemarketdata;

import com.ib.client.Types;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.dataobject.ContractDataTemplates;
import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.model.data.entity.LastPriceLiveMarketData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.repository.LastPriceLiveMarketDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LastPriceLiveMarketDataCreateService {

  private final PropertiesConfig propertiesConfig;
  private final LastPriceLiveMarketDataRepository lastPriceLiveMarketDataRepository;
  private final ContractDataRepository contractDataRepository;

  @Transactional
  public LastPriceLiveMarketData createLiveData(int tickerId, double price) {
    ContractData contractData = null;
    if (tickerId == propertiesConfig.getSpxTickerId()) {
      contractData =
          contractDataRepository
              .findFirstBySymbolAndSecurityTypeAndCurrency("SPX", Types.SecType.IND, "USD")
              .orElseGet(() -> contractDataRepository.save(ContractDataTemplates.SpxOptionData()));
    } else {
      throw new RuntimeException("No, ContractData with tickerId: " + tickerId + " found!");
    }
    LastPriceLiveMarketData lastPriceLiveMarketData =
        LastPriceLiveMarketData.builder()
            .tickerId((long) tickerId)
            .lastPrice(price)
            .contractData(contractData)
            .createDate(new Date(Instant.now().toEpochMilli()))
            .build();
    return lastPriceLiveMarketDataRepository.save(lastPriceLiveMarketData);
  }
}
