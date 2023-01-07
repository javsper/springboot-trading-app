package de.javsper.springboottradingdata.model.message;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseMessage extends IBKRDataTypeEntity {

    private Integer messageId;

    @Lob
    private String message;
}
