package ru.vasire;

import ru.vasire.machine.AcceptingFundsException;
import ru.vasire.machine.Banknote;
import ru.vasire.machine.BanknoteCell;
import ru.vasire.machine.CashMachine;
import ru.vasire.machine.CashMachineSimple;
import ru.vasire.machine.InsufficientFundsException;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InsufficientFundsException, AcceptingFundsException {
        CashMachine cashMachine = new CashMachineSimple(
                new BanknoteCell(Banknote.N100, 5),
                new BanknoteCell(Banknote.N500, 5),
                new BanknoteCell(Banknote.N1000, 5),
                new BanknoteCell(Banknote.N5000, 5));

        int initialBalance = cashMachine.checkBalance();
        System.out.println("Initial sum " + initialBalance);

        List<Banknote> takeMoney = cashMachine.getMoney(6600);
        System.out.println("Taken notes 6600" + takeMoney);

        initialBalance = cashMachine.checkBalance();
        System.out.println("New sum " + initialBalance);

        System.out.println("Put sum " + 6600);
        Banknote[] money = new Banknote[]{Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000};
        cashMachine.putMoney(List.of(money));
        System.out.println("Accept sum " + (cashMachine.checkBalance() - initialBalance));
        initialBalance = cashMachine.checkBalance();
        System.out.println("New sum " + initialBalance);

        System.out.println("");

    }
}
