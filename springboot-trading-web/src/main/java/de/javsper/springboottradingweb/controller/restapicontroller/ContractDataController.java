package de.javsper.springboottradingweb.controller.restapicontroller;

import de.javsper.springboottradingdata.model.data.StrategyContractData;
import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import de.javsper.springboottradingibkr.client.strategybuilder.StrategyBuilderService;
import de.javsper.springboottradingweb.service.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractDataController {

    private final ContractDataRepository contractDataRepository;
    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final StrategyBuilderService strategyBuilderService;
    private final ResponseMapper responseMapper;

    @PostMapping("/combo")
    public ResponseEntity<ContractDataDBO> ComboLegContractData(@RequestBody StrategyContractData strategyContractData) {

        Optional<ContractDataDBO> savedContract =
                strategyBuilderService.getComboLegContractData(strategyContractData);

        return responseMapper.mapResponse(savedContract);
    }


    @PostMapping("/single")
    public ResponseEntity<ContractDataDBO> saveContractData(@RequestBody ContractDataDBO contractDataDBO) {
        Optional<ContractDataDBO> savedContract = uniqueContractDataProvider.getExistingContractDataOrCallApi(
                contractDataDBO);
        return responseMapper.mapResponse(savedContract);
    }

    @GetMapping
    public ResponseEntity<ContractDataDBO> getContractDataById(@RequestParam("id") long id) {
        return responseMapper.mapResponse(contractDataRepository.findById(id));
    }
    @GetMapping("/contract-id")
    public ResponseEntity<ContractDataDBO> getContractDataByContractId(@RequestParam("id") int id) {
        return responseMapper.mapResponse(contractDataRepository.findFirstByContractId(id));
    }
}
