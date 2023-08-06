package ru.vasire.machine.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vasire.machine.model.Banknote;
import ru.vasire.machine.model.dto.AccountData;
import ru.vasire.machine.model.dto.AtmBanknoteData;
import ru.vasire.machine.model.dto.AtmMoneyData;
import ru.vasire.machine.service.AtmService;
import ru.vasire.machine.util.AccountDataValidator;
import ru.vasire.machine.util.AtmBanknoteDataValidator;
import ru.vasire.machine.util.AtmMoneyDataValidator;

@Controller
@RequestMapping("/atm")
public class AtmController {

    private final AtmService atmCashMachineService;
    private final AtmBanknoteDataValidator atmBanknoteDataValidator;
    private final AtmMoneyDataValidator atmMoneyDataValidator;

    private final AccountDataValidator accountDataValidator;

    public AtmController(AtmService atmCashMachineService, AtmBanknoteDataValidator atmBanknoteDataValidator, AtmMoneyDataValidator atmMoneyDataValidator, AccountDataValidator accountDataValidator) {
        this.atmCashMachineService = atmCashMachineService;
        this.atmBanknoteDataValidator = atmBanknoteDataValidator;
        this.atmMoneyDataValidator = atmMoneyDataValidator;
        this.accountDataValidator = accountDataValidator;
    }

    @GetMapping("/get_balance")
    public String getBalance(@ModelAttribute("request") AccountData accountData) {
        return "atm/balance";
    }

    @PostMapping("/get_balance")
    public String getBalanceAction(Model model, @ModelAttribute("request") @Valid AccountData accountData, BindingResult bindingResult)
    {
        accountDataValidator.validate(accountData, bindingResult);
        if(bindingResult.hasErrors())
            return "atm/balance";

        model.addAttribute("balance",  atmCashMachineService.getBalance(accountData));
        return "atm/balance_result";
    }

    @GetMapping("/get_money")
    public String getMoney(@ModelAttribute("request") @Valid AtmMoneyData atmGetMoneyRequest) {
        return "atm/get_money";
    }

    @PostMapping("/get_money")
    public String getMoneyAction(Model model, @ModelAttribute("request") @Valid AtmMoneyData atmGetMoney,
                                 BindingResult bindingResult)
    {
        atmMoneyDataValidator.validate(atmGetMoney, bindingResult);

        if(bindingResult.hasErrors())
            return "atm/get_money";

        try{
            model.addAttribute("bundles",  atmCashMachineService.getMoney(atmGetMoney));
        }catch (Exception ex){
            bindingResult.addError(new FieldError("request", "sum", ex.getMessage()));
            return "atm/get_money";
        }
        return "atm/get_money_result";
    }

    @GetMapping("/put_money")
    public String putMoney(Model model, @ModelAttribute("request") @Valid AtmBanknoteData atmBanknoteData) {
        model.addAttribute("banknoteKind", Banknote.values());
        return "atm/put_money";
    }

    @PostMapping("/put_money")
    public String putMoneyAction(Model model, @ModelAttribute("request") @Valid AtmBanknoteData atmBanknoteData,
                                 BindingResult bindingResult)
    {
        model.addAttribute("banknoteKind", Banknote.values());
        atmBanknoteDataValidator.validate(atmBanknoteData, bindingResult);

        if(bindingResult.hasErrors()) {
            return "atm/put_money";
        }
        try{
            model.addAttribute("balance",  atmCashMachineService.putMoney(atmBanknoteData));
            return "atm/balance_result";
        }catch (Exception ex){
            bindingResult.addError(new FieldError("request", "banknoteCount", ex.getMessage()));
            return "atm/put_money";
        }
    }

}
