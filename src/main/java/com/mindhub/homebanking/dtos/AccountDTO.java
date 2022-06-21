package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    public AccountDTO (){}

    private long id;

    String number;
    LocalDateTime creationDate;
    double balance;

    private boolean disableAccount;

    AccountType accountType;

    Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO (Account account){
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.id = account.getId();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.disableAccount = account.isDisableAccount();
        this.accountType = account.getAccountType();
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean isDisableAccount() {
        return disableAccount;
    }

    public void setDisableAccount(boolean disableAccount) {
        this.disableAccount = disableAccount;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
