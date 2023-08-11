package ru.vasire.machine.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDto {
    @NotEmpty(message = "The card number field cannot be empty")
    private String cardNumber;

    @NotEmpty(message = "The pin Code field cannot be empty")
    private String pinCode;
}
