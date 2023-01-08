package de.javsper.springboottradingibkr.client.service.position;

import de.javsper.springboottradingdata.model.entity.PositionData;
import de.javsper.springboottradingdata.service.apiresponsecheck.noinput.PositionApiResponseChecker;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    private final ApiCallerWithoutParameter<PositionData> positionApiCaller;
    private final PositionApiResponseChecker positionDataApiResponseChecker;
    private final PortfolioUpdateService portfolioUpdateService;

    public PositionService(ApiCallerWithoutParameter<PositionData> positionApiCaller,
                           PositionApiResponseChecker positionDataApiResponseChecker,
                           PortfolioUpdateService portfolioUpdateService) {
        this.positionApiCaller = positionApiCaller;
        this.positionDataApiResponseChecker = positionDataApiResponseChecker;
        this.portfolioUpdateService = portfolioUpdateService;
    }

    public List<PositionData> getUpdatedPortfolio() {
        positionApiCaller.callApi();
        List<PositionData> positions = positionDataApiResponseChecker.checkForApiResponseAndUpdate();
        return portfolioUpdateService.updatePortfolio(positions);
    }
}
