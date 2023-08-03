package ru.vasire.machine.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.AtmData;
import ru.vasire.machine.service.AccountService;

@RequiredArgsConstructor
@Component
public class AtmRequestValidator implements Validator {

    private final AccountService accountService;



    @Override
    public boolean supports(Class<?> clazz) {
        return AtmData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AtmData atmGetMoneyRequest = (AtmData) target;
        try{
            accountService.getAccount(atmGetMoneyRequest.getCardNumber(), atmGetMoneyRequest.getPinCode());
        } catch (Exception ex){
            errors.rejectValue("cardNumber", null, ex.getMessage());
            return;
        }

        Integer balance = accountService.getBalance(new Account(atmGetMoneyRequest.getCardNumber(), atmGetMoneyRequest.getPinCode())).intValue();
        if(atmGetMoneyRequest.getSum() > balance){
            errors.rejectValue("sum", null, "Requested amount exceeds balance");
            return;
        }
    }
}
