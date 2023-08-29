package ru.vasire.machine.sessionmanager;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

    <T> void doCommandInTransaction(TransactionAction<T> action);
}
