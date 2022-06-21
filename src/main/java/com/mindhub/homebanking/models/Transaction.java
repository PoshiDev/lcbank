package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    double amount;

    String description;

    LocalDateTime date;

    TransactionType type;

    double currentBalance;

    boolean disableTransactions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "account_id")
    private Account account;

    public Transaction(){}

    public Transaction(double amount, String description, LocalDateTime date, TransactionType type, Account account, double currentBalance, boolean disableTransactions){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.account = account;
        this.currentBalance = currentBalance;
        this.disableTransactions = disableTransactions;
    }

    public boolean isDisableTransactions() {
        return disableTransactions;
    }

    public void setDisableTransactions(boolean disableTransactions) {
        this.disableTransactions = disableTransactions;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    //public void setCurrentBalance(double currentBalance) {
        //this.currentBalance = currentBalance;
    //}

    public long getId() {
        return id;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    /* public void setType(TransactionType type) {
        this.type = type;
    } */

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
