package de.javsper.springboottradingibkr.client.service.position;

import de.javsper.springboottradingdata.model.PositionData;
import de.javsper.springboottradingdata.repository.PositionDataRepository;
import de.javsper.springboottradingdata.service.apiresponsecheck.PositionApiResponseCheckerDataApiResponseChecker;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.ib.client.EWrapperMsgGenerator.updatePortfolio;

@Service
public class PositionService {

    private final ApiCallerWithoutParameter<PositionData> positionApiCaller;
    private final PositionApiResponseCheckerDataApiResponseChecker positionDataApiResponseChecker;
    private final PortfolioUpdateService portfolioUpdateService;

    public PositionService(ApiCallerWithoutParameter<PositionData> positionApiCaller,
                           PositionApiResponseCheckerDataApiResponseChecker positionDataApiResponseChecker,
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
