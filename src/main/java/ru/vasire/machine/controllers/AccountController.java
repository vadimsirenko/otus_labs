package ru.vasire.machine.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.AccountChangePinCodeData;
import ru.vasire.machine.service.AccountService;
import ru.vasire.machine.util.AccountChangePinCodeValidator;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountChangePinCodeValidator accountChangePinCodeValidator;

    public AccountController(AccountService accountService, AccountChangePinCodeValidator accountChangePinCodeValidator) {
        this.accountService = accountService;
        this.accountChangePinCodeValidator = accountChangePinCodeValidator;
    }


    @GetMapping("/change_pin")
    public String changePinCode(@ModelAttribute("request") @Valid AccountChangePinCodeData accountChangePinCodeData) {
        return "account/change_pin";
    }

    @PatchMapping("/change_pin")
    public String changePinCodeAction(@ModelAttribute("request") @Valid AccountChangePinCodeData accountChangePinCodeData,
                                      BindingResult bindingResult)
    {
        accountChangePinCodeValidator.validate(accountChangePinCodeData, bindingResult);

        if(bindingResult.hasErrors()) {
            return "account/change_pin";
        }
        try{
            accountService.changePinCode(new Account(accountChangePinCodeData.getCardNumber(), accountChangePinCodeData.getPinCode()), accountChangePinCodeData.getNewPinCode());
            return "account/change_pin_result";
        }catch (Exception ex){
            bindingResult.addError(new FieldError("request", "newPinCode", ex.getMessage()));
            return "account/change_pin";
        }
    }
}
