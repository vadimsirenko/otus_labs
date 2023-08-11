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
import ru.vasire.machine.model.dto.AtmBanknoteDto;
import ru.vasire.machine.model.dto.AtmMoneyDto;
import ru.vasire.machine.model.dto.CardDto;
import ru.vasire.machine.service.AtmService;
import ru.vasire.machine.util.AtmBanknoteValidator;
import ru.vasire.machine.util.AtmMoneyValidator;
import ru.vasire.machine.util.CardValidator;

@Controller
@RequestMapping("/atm")
public class AtmController {

    private static final String ATM_BALANCE_VIEW = "atm/balance";
    private static final String ATM_BALANCE_RESULT_VIEW = "atm/balance_result";
    private static final String ATM_PUT_MONYE_VIEW = "atm/put_money";
    private static final String ATM_GET_MONYE_VIEW = "atm/get_money";
    private static final String ATM_GET_MONYE_RESULT_VIEW = "atm/get_money_result";
    private final AtmService atmCashMachineService;
    private final AtmBanknoteValidator atmBanknoteDataValidator;
    private final AtmMoneyValidator atmMoneyDataValidator;
    private final CardValidator accountDataValidator;


    public AtmController(AtmService atmCashMachineService, AtmBanknoteValidator atmBanknoteDataValidator, AtmMoneyValidator atmMoneyDataValidator, CardValidator accountDataValidator) {
        this.atmCashMachineService = atmCashMachineService;
        this.atmBanknoteDataValidator = atmBanknoteDataValidator;
        this.atmMoneyDataValidator = atmMoneyDataValidator;
        this.accountDataValidator = accountDataValidator;
    }

    @GetMapping("/get_balance")
    public String getBalance(@ModelAttribute("request") CardDto card) {
        return ATM_BALANCE_VIEW;
    }

    @PostMapping("/get_balance")
    public String getBalanceAction(Model model, @ModelAttribute("request") @Valid CardDto card, BindingResult bindingResult) {
        accountDataValidator.validate(card, bindingResult);
        if (bindingResult.hasErrors()) {
            return ATM_BALANCE_VIEW;
        }
        model.addAttribute("balance", atmCashMachineService.getBalance(card));
        return ATM_BALANCE_RESULT_VIEW;
    }

    @GetMapping("/get_money")
    public String getMoney(@ModelAttribute("request") @Valid AtmMoneyDto atmGetMoneyRequest) {
        return ATM_GET_MONYE_VIEW;
    }

    @PostMapping("/get_money")
    public String getMoneyAction(Model model, @ModelAttribute("request") @Valid AtmMoneyDto atmGetMoney,
                                 BindingResult bindingResult) {
        atmMoneyDataValidator.validate(atmGetMoney, bindingResult);
        if (bindingResult.hasErrors()) {
            return ATM_GET_MONYE_VIEW;
        }
        try {
            model.addAttribute("bundles", atmCashMachineService.getMoney(atmGetMoney));
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("request", "sum", ex.getMessage()));
            return ATM_GET_MONYE_VIEW;
        }
        return ATM_GET_MONYE_RESULT_VIEW;
    }

    @GetMapping("/put_money")
    public String putMoney(Model model, @ModelAttribute("request") @Valid AtmBanknoteDto atmBanknoteData) {
        model.addAttribute("banknoteKind", Banknote.values());
        return ATM_PUT_MONYE_VIEW;
    }

    @PostMapping("/put_money")
    public String putMoneyAction(Model model, @ModelAttribute("request") @Valid AtmBanknoteDto atmBanknoteData,
                                 BindingResult bindingResult) {
        model.addAttribute("banknoteKind", Banknote.values());
        atmBanknoteDataValidator.validate(atmBanknoteData, bindingResult);
        if (bindingResult.hasErrors()) {
            return ATM_PUT_MONYE_VIEW;
        }
        try {
            model.addAttribute("balance", atmCashMachineService.putMoney(atmBanknoteData));
            return ATM_BALANCE_RESULT_VIEW;
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("request", "banknoteCount", ex.getMessage()));
            return ATM_PUT_MONYE_VIEW;
        }
    }

}
