package ru.vasire.machine.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vasire.machine.model.Banknote;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtmBanknoteDto extends CardDto {
    @NotEmpty(message = "Banknote count cannot be empty")
    @Positive(message = "The banknote count must be positive")
    private int banknoteCount;

    private Banknote banknote;
}
