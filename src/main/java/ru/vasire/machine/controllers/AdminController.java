package ru.vasire.machine.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vasire.machine.service.AccountService;

@Controller
@RequestMapping("")
public class AdminController {
    private final AccountService accountService;

    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAccounts());
        return "admin/get_accounts_result";
    }
}
