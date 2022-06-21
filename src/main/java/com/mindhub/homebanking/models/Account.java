package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    String number;
    LocalDateTime creationDate;
    double balance;

    AccountType accountType;

    private boolean disableAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account (){}

    public Account (String number, LocalDateTime creationDate, double balance, Client client, boolean disableAccount, AccountType accountType){
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.disableAccount = disableAccount;
        this.accountType = accountType;

    }
    @JsonIgnore
    public Client getClient(){
        return client;
    }
    public void setClient(Client client) {
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction (Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

}
