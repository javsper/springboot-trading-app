package de.javsper.springboottradingweb.controller.restapicontroller.portfolio;

import de.javsper.springboottradingdata.model.entity.PositionData;
import de.javsper.springboottradingdata.model.entity.ProfitAndLossData;
import de.javsper.springboottradingibkr.client.service.position.PositionService;
import de.javsper.springboottradingibkr.client.service.position.profitandloss.PositionPnLService;
import de.javsper.springboottradingweb.service.ResponseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PositionService positionService;
    private final ResponseMapper responseMapper;
    private final PositionPnLService positionPnLService;

    public PortfolioController(PositionService positionService, ResponseMapper responseMapper, PositionPnLService positionPnLService) {
        this.positionService = positionService;
        this.responseMapper = responseMapper;
        this.positionPnLService = positionPnLService;
    }

    @GetMapping
    public ResponseEntity<List<PositionData>> getPortfolio(){
        return responseMapper.mapResponse(positionService.getUpdatedPortfolio());
    }
    @GetMapping("/pnl")
    public ResponseEntity<List<ProfitAndLossData>>getPnL(){
        return responseMapper.mapResponse(positionPnLService.getPortfolioPnL());
    }
}
