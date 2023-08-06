package ru.vasire.machine.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountChangePinCodeData extends AccountData{
    @NotEmpty(message = "The new pin Code field cannot be empty")
    private String newPinCode;

    @NotEmpty(message = "The new pin Code 2 field cannot be empty")
    private String newPinCode2;

}
