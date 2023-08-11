package ru.vasire.machine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CardDetailsDto {
    private String accuntNumber;

    private String cardNumber;

    private String pinCode;

    private BigDecimal balance;
}
