package de.javsper.springboottradingdata.model.data.kafka;

import de.javsper.springboottradingdata.model.subtype.Symbol;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionChainData extends KafkaDataType {

  private Long lastTradeDate;
  private Symbol symbol;
  private OptionListData calls;
  private OptionListData puts;
  
}
