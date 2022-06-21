package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    public TransactionDTO(){}

    private long id;

    double amount;

    String description;

    LocalDateTime date;

    double currentBalance;

    private TransactionType type;

    boolean disableTransactions;

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.type = transaction.getType();
        this.currentBalance = transaction.getCurrentBalance();
        this.disableTransactions = transaction.isDisableTransactions();
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

    public void setId(long id) {
        this.id = id;
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

    public void setType(TransactionType type) {
        this.type = type;
    }
}
