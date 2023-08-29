package ru.vasire.machine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailsDto {
    private String accuntNumber;

    private String cardNumber;

    private String pinCode;

    private BigDecimal balance;
}
