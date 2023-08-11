package ru.vasire.machine.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vasire.machine.service.AccountService;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AdminController {
    private static final String ADMIN_GET_ACCOUNTS_RESULT_VIEW = "admin/get_accounts_result";
    private final AccountService accountService;

    @GetMapping("")
    public String getAccounts(Model model) {
        model.addAttribute("cards", accountService.getCardDetails());
        return ADMIN_GET_ACCOUNTS_RESULT_VIEW;
    }
}
