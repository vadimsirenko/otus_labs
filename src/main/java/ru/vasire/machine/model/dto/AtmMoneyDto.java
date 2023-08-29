package ru.vasire.machine.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtmMoneyDto extends CardDto {
    @NotEmpty(message = "Requested amount cannot be empty")
    @Positive(message = "The sum must be positive")
    private int sum;
}
