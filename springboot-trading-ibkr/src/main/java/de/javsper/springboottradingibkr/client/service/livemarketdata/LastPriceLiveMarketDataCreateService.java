package de.javsper.springboottradingibkr.client.service.livemarketdata;

import com.ib.client.Types;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.dataobject.ContractDataTemplates;
import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.model.data.entity.LastPriceLiveMarketDataDBO;
import de.javsper.springboottradingdata.model.subtype.Symbol;
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
  public LastPriceLiveMarketDataDBO createLiveData(int tickerId, double price) {
    ContractDataDBO contractDataDBO;
      contractDataDBO =
          contractDataRepository
              .findFirstBySymbolAndSecurityTypeAndCurrency(Symbol.SPX, Types.SecType.IND, "USD")
              .orElseGet(() -> contractDataRepository.save(ContractDataTemplates.SpxOptionData()));
    LastPriceLiveMarketDataDBO lastPriceLiveMarketDataDBO =
        LastPriceLiveMarketDataDBO.builder()
            .tickerId((long) tickerId)
            .lastPrice(price)
            .contractDataDBO(contractDataDBO)
            .createDate(new Date(Instant.now().toEpochMilli()))
            .build();
    return lastPriceLiveMarketDataRepository.save(lastPriceLiveMarketDataDBO);
  }
}
