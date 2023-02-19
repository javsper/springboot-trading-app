package de.javsper.springboottradingweb.controller.restapicontroller;

import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.service.LegMapService;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import de.javsper.springboottradingibkr.client.strategybuilder.StrategyBuilderService;
import de.javsper.springboottradingweb.service.ResponseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/contract")
public class ContractDataController {

    private final ContractDataRepository contractDataRepository;
    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final StrategyBuilderService strategyBuilderService;
    private final LegMapService legMapService;
    private final ResponseMapper responseMapper;

    public ContractDataController(ContractDataRepository contractDataRepository,
                                  UniqueContractDataProvider uniqueContractDataProvider,
                                  StrategyBuilderService strategyBuilderService, LegMapService legMapService, ResponseMapper responseMapper) {
        this.contractDataRepository = contractDataRepository;
        this.uniqueContractDataProvider = uniqueContractDataProvider;
        this.strategyBuilderService = strategyBuilderService;
        this.legMapService = legMapService;
        this.responseMapper = responseMapper;
    }

    @PutMapping("/combo")
    public ResponseEntity<ContractData> ComboLegContractData(@RequestBody ContractData contractData,
                                                             @RequestParam(defaultValue = "0", name = "buyPutStrike") double buyPutStrike,
                                                             @RequestParam(defaultValue = "0", name = "sellPutStrike") double sellPutStrike,
                                                             @RequestParam(defaultValue = "0", name = "buyCallStrike") double buyCallStrike,
                                                             @RequestParam(defaultValue = "0", name = "sellCallStrike") double sellCallStrike,
                                                             @RequestParam(defaultValue = "0", name = "buyPutStrikeTwo") double buyPutStrikeTwo,
                                                             @RequestParam(defaultValue = "0", name = "sellPutStrikeTwo") double sellPutStrikeTwo,
                                                             @RequestParam(defaultValue = "0", name = "buyCallStrikeTwo") double buyCallStrikeTwo,
                                                             @RequestParam(defaultValue = "0", name = "sellCallStrikeTwo") double sellCallStrikeTwo) {

        //TODO will have to implement solution to get combos like in Orders Controller



        Optional<ContractData> savedContract = strategyBuilderService.getComboLegContractData(contractData,  legMapService.mapLegs(buyPutStrike,
                sellPutStrike,
                buyCallStrike,
                sellCallStrike,
                buyPutStrikeTwo,
                sellPutStrikeTwo,
                buyCallStrikeTwo,
                sellCallStrikeTwo));

        return responseMapper.mapResponse(savedContract);
    }


    @PutMapping()
    public ResponseEntity<ContractData> saveContractData(@RequestBody ContractData contractData) {
        Optional<ContractData> savedContract = uniqueContractDataProvider.getExistingContractDataOrCallApi(contractData);
        return responseMapper.mapResponse(savedContract);
    }

    @GetMapping
    public ResponseEntity<ContractData> getContractDataById(@RequestParam("id") long id) {
        return responseMapper.mapResponse(contractDataRepository.findById(id));
    }
}
