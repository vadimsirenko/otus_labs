package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.AccountData;
import ru.vasire.machine.service.AccountService;

@Component
public class AccountDataBaseValidator<T extends AccountData> implements Validator {

    protected final AccountService accountService;

    public AccountDataBaseValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        T accountData = (T) target;
        Account account;
        try{
            account = accountService.getAccountByCardNumber(accountData.getCardNumber());
            if(account == null) {
                errors.rejectValue("cardNumber", null, "Invalid card number");
                return;
            }
        } catch (Exception ex){

            errors.rejectValue("cardNumber", null, ex.getMessage());
            return;
        }
        try{
            if(!accountService.validatePinCode(account, accountData.getPinCode())){
                errors.rejectValue("pinCode", null, "Invalid Pin Code number");
                return;
            }
        } catch (Exception ex){
            errors.rejectValue("pinCode", null, ex.getMessage());
            return;
        }
    }
}
