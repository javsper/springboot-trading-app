package de.javsper.springboottradingdata.model.data.kafka;

import de.javsper.springboottradingdata.model.subtype.Symbol;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaOptionChainData extends KafkaDataType {

    private String lastTradeDate;
    private Symbol symbol;
    private KafkaOptionListData calls;
    private KafkaOptionListData puts;
}
