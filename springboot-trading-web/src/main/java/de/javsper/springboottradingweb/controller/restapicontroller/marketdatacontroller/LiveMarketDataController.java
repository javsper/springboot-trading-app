package de.javsper.springboottradingweb.controller.restapicontroller.marketdatacontroller;

import com.ib.client.Types;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingibkr.client.service.MarketDataService;
import de.javsper.springboottradingweb.service.ResponseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market-data")
public class LiveMarketDataController {

    private final MarketDataService marketDataService;
    private final ResponseMapper responseMapper;

    public LiveMarketDataController(MarketDataService marketDataService, ResponseMapper responseMapper) {
        this.marketDataService = marketDataService;
        this.responseMapper = responseMapper;
    }

    //TestCode to be deleted later on
    @GetMapping("/test")
    public ResponseEntity<ContractData> handleRealTimeDataTest(){
        ContractData contractData = ContractData.builder().securityType(Types.SecType.CASH).symbol("EUR").exchange("IDEALPRO").currency("GBP").build();
        return responseMapper.mapResponse(marketDataService.requestLiveMarketDataForContractData(contractData));

    }
    @GetMapping
    public ResponseEntity<ContractData> handleRealTimeData(@RequestBody ContractData contractData){
        return responseMapper.mapResponse(marketDataService.requestLiveMarketDataForContractData(contractData));

    }
}
