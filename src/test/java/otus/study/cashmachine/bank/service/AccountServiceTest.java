package otus.study.cashmachine.bank.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.bank.dao.AccountDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountServiceImpl accountServiceImpl;

    @Test
    // @TODO test account creation with mock and ArgumentMatcher
    void createAccountMock() {
        int expectedAccountId = Integer.MAX_VALUE;
        BigDecimal expectedAmount = BigDecimal.TEN;

        Mockito.when(accountDao.saveAccount(any()))
                .thenAnswer(input -> new Account(expectedAccountId, ((Account) input.getArgument(0)).getAmount()));

        Account testAccount = accountServiceImpl.createAccount(expectedAmount);
        // Проверяем, что сохраняется сумма и формируется Id
        Assertions.assertEquals(testAccount.getAmount(), expectedAmount);
        Assertions.assertEquals(testAccount.getId(), expectedAccountId);
        // Проверяем, что вызывали сохранение и один раз
        Mockito.verify(accountDao, Mockito.times(1))
                .saveAccount(any());
    }

    @Test
    //  @TODO test account creation with ArgumentCaptor
    void createAccountCaptor() {
        int expectedAccountId = Integer.MAX_VALUE;
        BigDecimal expectedAmount = BigDecimal.TEN;

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        Mockito.when(accountDao.saveAccount(accountCaptor.capture()))
                .thenReturn(new Account(expectedAccountId,expectedAmount));

        Account testAccount = accountServiceImpl.createAccount(expectedAmount);
        // Проверяем, что в параметр метода accountDao.saveAccount передается сумма
        Assertions.assertEquals(accountCaptor.getValue().getAmount(), expectedAmount);
        // Проверяем, что вызывали сохранение и один раз
        Mockito.verify(accountDao, Mockito.times(1))
                .saveAccount(any());

    }

    @Test
    void addSum() {
    }

    @Test
    void getSum() {
    }

    @Test
    void getAccount() {
    }

    @Test
    void checkBalance() {
    }
}
