package ru.vasire.machine.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vasire.machine.model.dto.CardChangePinCodeDto;
import ru.vasire.machine.service.CardService;
import ru.vasire.machine.util.CardChangePinCodeValidator;

@Controller
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private static final String CHANGE_PIN_VIEW = "card/change_pin";
    private static final String CHANGE_PIN_RESULT_VIEW = "card/change_pin_result";
    private final CardService cardService;
    private final CardChangePinCodeValidator accountChangePinCodeValidator;

    @GetMapping("/change_pin")
    public String changePinCode(@ModelAttribute("request") @Valid CardChangePinCodeDto accountChangePinCodeData) {
        return CHANGE_PIN_VIEW;
    }

    @PatchMapping("/change_pin")
    public String changePinCodeAction(@ModelAttribute("request") @Valid CardChangePinCodeDto accountChangePinCodeData,
                                      BindingResult bindingResult) {
        accountChangePinCodeValidator.validate(accountChangePinCodeData, bindingResult);
        if (bindingResult.hasErrors()) {
            return CHANGE_PIN_VIEW;
        }
        try {
            cardService.changePinCode(accountChangePinCodeData.getCardNumber(), accountChangePinCodeData.getPinCode(), accountChangePinCodeData.getNewPinCode());
            return CHANGE_PIN_RESULT_VIEW;
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("request", "newPinCode", ex.getMessage()));
            return CHANGE_PIN_VIEW;
        }
    }
}