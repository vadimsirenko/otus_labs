package ru.vasire.machine.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    @NotEmpty(message = "The card number field cannot be empty")
    private String cardNumber;

    @NotEmpty(message = "The pin Code field cannot be empty")
    private String pinCode;
}
