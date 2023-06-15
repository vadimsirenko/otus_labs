package ru.vasire;

import ru.vasire.machine.*;


import java.util.List;

public class Main {

    public static void main(String[] args) throws InsufficientFundsException, AcceptingFundsException {
        CashMachine cashMachine = new CashMachineSimple(
                new MoneyCell(Banknote.N100, 5),
                new MoneyCell(Banknote.N500, 5),
                new MoneyCell(Banknote.N1000, 5),
                new MoneyCell(Banknote.N5000, 5));

        int initialBalance = cashMachine.checkBalance();
        System.out.println("Initial sum " + initialBalance);

        List<Banknote> takeMoney = cashMachine.getMoney(6600);
        System.out.println("Taken notes 6600" + takeMoney);

        initialBalance = cashMachine.checkBalance();
        System.out.println("New sum " + initialBalance);

        System.out.println("Put sum " + 6600);
        int acceptSum = cashMachine.putMoney(Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000);
        System.out.println("Accept sum " + initialBalance);
        initialBalance = cashMachine.checkBalance();
        System.out.println("New sum " + initialBalance);

        System.out.println("");

    }
}
