package de.javsper.springboottradingibkr.client.service.livemarketdata;

import com.ib.client.Types;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.constants.AutoDayTradeConstants;
import de.javsper.springboottradingdata.dataobject.ContractDataTemplates;
import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingdata.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TickerIdToContractService {

    private final ContractRepository contractRepository;
    private final PropertiesConfig propertiesConfig;
    private final LastTradeDateBuilder lastTradeDateBuilder;

    public ContractDbo resolveTickerId(int tickerId){
        if (tickerId == propertiesConfig.getSpxTickerId()){
            return contractRepository
                    .findFirstBySymbolAndSecurityTypeAndCurrency(Symbol.SPX, Types.SecType.IND, "USD")
                    .orElseGet(() -> contractRepository.save(ContractDataTemplates.SpxOptionData()));
        }else if(tickerId == lastTradeDateBuilder.getDateIntFromToday()){
            String search =
                    lastTradeDateBuilder.getDateStringFromToday()+ AutoDayTradeConstants.DELIMITER+Symbol.SPX.name();
            return contractRepository.findByComboLegsDescriptionContains(search).get(0);
        }else{
            throw new RuntimeException("No Contract fo Ticker found");
        }
    }
}
