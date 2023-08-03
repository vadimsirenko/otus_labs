package ru.vasire.machine.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.service.AccountService;

@RequiredArgsConstructor
@Component
public class AccountValidator implements Validator {

    private final AccountService accountService;



    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        try{
            accountService.getAccount(account.getCardNumber(), account.getPinCode());
        } catch (Exception ex){
            errors.rejectValue("cardNumber", null, ex.getMessage());
        }
    }
}
