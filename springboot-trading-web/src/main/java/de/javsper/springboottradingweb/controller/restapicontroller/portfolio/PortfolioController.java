package de.javsper.springboottradingweb.controller.restapicontroller.portfolio;

import de.javsper.springboottradingdata.model.entity.PositionData;
import de.javsper.springboottradingibkr.client.service.position.PositionService;
import de.javsper.springboottradingweb.service.ResponseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PositionService positionService;
    private final ResponseMapper responseMapper;

    public PortfolioController(PositionService positionService, ResponseMapper responseMapper) {
        this.positionService = positionService;
        this.responseMapper = responseMapper;
    }

    @GetMapping
    public ResponseEntity<List<PositionData>> getPortfolio(){
        return responseMapper.mapResponse(positionService.getUpdatedPortfolio());
    }
}
