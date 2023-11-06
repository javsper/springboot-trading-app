package de.javsper.springboottradingibkr.client.service.livemarketdata;

import com.ib.client.Types;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.dataobject.ContractDataTemplates;
import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.model.data.entity.LastPriceLiveMarketDataDbo;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.repository.ContractRepository;
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
  private final ContractRepository contractRepository;

  @Transactional
  public LastPriceLiveMarketDataDbo createLiveData(int tickerId, double price) {
    ContractDbo contractDBO;
      contractDBO =
          contractRepository
              .findFirstBySymbolAndSecurityTypeAndCurrency(Symbol.SPX, Types.SecType.IND, "USD")
              .orElseGet(() -> contractRepository.save(ContractDataTemplates.SpxOptionData()));
    LastPriceLiveMarketDataDbo lastPriceLiveMarketDataDbo =
        LastPriceLiveMarketDataDbo.builder()
            .tickerId((long) tickerId)
            .lastPrice(price)
            .contractDBO(contractDBO)
            .createDate(new Date(Instant.now().toEpochMilli()))
            .build();
    return lastPriceLiveMarketDataRepository.save(lastPriceLiveMarketDataDbo);
  }
}
