package de.javsper.springboottradingdata.model.data.entity;

import de.javsper.springboottradingdata.model.data.IBKRDataType;
import de.javsper.springboottradingdata.model.subtype.Symbol;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionChainDataDBO extends IBKRDataType {

        @Id
        private String lastTradeDate;
        @NotNull
        private Symbol symbol;
        @OneToOne
        private OptionListDBO calls;
        @OneToOne
        private OptionListDBO puts;
    }
