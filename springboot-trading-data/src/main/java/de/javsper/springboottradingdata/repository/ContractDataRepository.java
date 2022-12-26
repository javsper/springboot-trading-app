package de.javsper.springboottradingdata.repository;

import com.ib.client.Types;
import de.javsper.springboottradingdata.model.ContractData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ContractDataRepository  extends IBKRDataTypeRepository<ContractData> {

    public List<ContractData> findAllByContractId(Integer id);
    public Optional<ContractData> findFirstByContractId(Integer id);

    public Optional<ContractData> findFirstByLastTradeDateAndSymbolAndStrikeAndRight(String last, String symbol, BigDecimal strike, Types.Right right);
}
