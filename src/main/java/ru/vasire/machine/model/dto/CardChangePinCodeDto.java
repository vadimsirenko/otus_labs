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
public class CardChangePinCodeDto extends CardDto {
    @NotEmpty(message = "The new pin Code field cannot be empty")
    private String newPinCode;

    @NotEmpty(message = "The new pin Code 2 field cannot be empty")
    private String newPinCode2;

}
