package ru.vasire.machine.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vasire.machine.model.BanknoteBundle;
import ru.vasire.machine.model.dto.AtmData;
import ru.vasire.machine.service.AtmCashMachineService;
import ru.vasire.machine.util.AtmRequestValidator;

import java.util.List;

@Controller
@RequestMapping("/atm")
@RequiredArgsConstructor
public class AtmController {

    private final AtmCashMachineService atmCashMachineService;
    //private final AccountValidator accountValidator;
    private final AtmRequestValidator atmRequestValidator;

     @GetMapping()
    public String betBalance(Model model) {
        model.addAttribute("request", new AtmData());
        return "atm/index";
    }

    @PostMapping()
    public String betBalanceAction(Model model, @ModelAttribute("request") @Valid AtmData atmRequest, BindingResult bindingResult)
    {
        atmRequestValidator.validate(atmRequest, bindingResult);
        if(bindingResult.hasErrors())
            return "atm/index";

        atmRequest.setBalance(atmCashMachineService.getBalance(atmRequest));
        return "atm/index";
    }

    @GetMapping("/get")
    public String getMoney(@ModelAttribute("request") @Valid AtmData atmGetMoneyRequest) {
        return "atm/get";
    }

    @PostMapping("/getMoney")
    public String getMoneyAction(Model model, @ModelAttribute("request") @Valid AtmData atmGetMoneyRequest,
                                 BindingResult bindingResult)
    {
        atmRequestValidator.validate(atmGetMoneyRequest, bindingResult);

        if(bindingResult.hasErrors())
            return "atm/get";
        List<BanknoteBundle> bundles;
        try{
            bundles = atmCashMachineService.getMoney(atmGetMoneyRequest);
        }catch (Exception ex){
            bindingResult.addError(new FieldError("request", "sum", ex.getMessage()));
            return "atm/get";
        }
        model.addAttribute("bundles", bundles);
        return "atm/get_money";
    }
}
