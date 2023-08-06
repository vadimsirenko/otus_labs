package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.dto.AccountData;
import ru.vasire.machine.service.AccountService;

@Component
public class AccountDataValidator extends AccountDataBaseValidator<AccountData> {

    public AccountDataValidator(AccountService accountService) {
        super(accountService);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        super.validate(target, errors);
    }
}
