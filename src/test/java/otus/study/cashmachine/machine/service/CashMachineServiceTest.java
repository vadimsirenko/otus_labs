package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import otus.study.cashmachine.TestUtil;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {

    @Spy
    @InjectMocks
    private CardServiceImpl cardService;


    @Mock
    private CardsDao cardsDao;

    @Mock
    private AccountService accountService;

    @Mock
    private MoneyBoxService moneyBoxService;

    private CashMachineServiceImpl cashMachineService;

    private CashMachine cashMachine = new CashMachine(new MoneyBox());

    @BeforeEach
    void init() {
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
    }


    @Test
    // @TODO create get money test using spy as mock
    void getMoney() {

        List<Integer> expectedList =  new ArrayList<>(List.of(1,1,1,1));

        when(moneyBoxService.getMoney(any(), anyInt())).thenReturn(expectedList);

        doReturn(BigDecimal.valueOf(6600))
                .when(cardService).getMoney("cardnum1", "0000", BigDecimal.valueOf(6600));

        List<Integer> testList = cashMachineService.getMoney(
                cashMachine,
                "cardnum1",
                "0000",
                BigDecimal.valueOf(6600));
        assertEquals(expectedList, testList);

    }

    @Test
    void putMoney() {
    }

    @Test
    void checkBalance() {

    }

    @Test
    // @TODO create change pin test using spy as implementation and ArgumentCaptor and thenReturn
    void changePin() {

        ArgumentCaptor<Card> cardArgumentCaptor = ArgumentCaptor.forClass(Card.class);

        when(cardsDao.getCardByNumber("cardnum1"))
                .thenReturn(new Card(1,"cardnum1",1L, TestUtil.getHash("oldPin")));

        when(cardsDao.saveCard(cardArgumentCaptor.capture()))
                .thenReturn(new Card(1,"cardnum1",1L,"newPin"));

        boolean testResult = cashMachineService.changePin("cardnum1","oldPin", "newPin");

        // Проверка отсутствия ошибок
        assertEquals(Boolean.TRUE, testResult);
        // Проверка, что сохраняем новый пинкод
        assertEquals(cardArgumentCaptor.getValue().getPinCode(), TestUtil.getHash("newPin"));

    }

    @Test
    // @TODO create change pin test using spy as implementation and mock an thenAnswer
    void changePinWithAnswer() {


        when(cardsDao.getCardByNumber("cardnum1"))
                .thenReturn(new Card(1,"cardnum1",1L, TestUtil.getHash("oldPin")));

        List<Card> expList = new ArrayList<>();
        when(cardsDao.saveCard(any())).thenAnswer(new Answer<Card>() {
            @Override
            public Card answer(InvocationOnMock invocation) throws Throwable {
                expList.add(invocation.getArgument(0));
                return invocation.getArgument(0);
            }
        });

        boolean testResult = cashMachineService.changePin("cardnum1","oldPin", "newPin");

        // Проверка отсутствия ошибок
        assertEquals(Boolean.TRUE, testResult);
        // Проверка, что сохраняем новый пинкод
        assertEquals(expList.get(0).getPinCode(), TestUtil.getHash("newPin"));
    }
}