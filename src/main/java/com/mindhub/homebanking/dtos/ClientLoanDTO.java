package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {

    public ClientLoanDTO(){}

    private long id;

    private String name;

    private int payment;

    private int amount;

    private long loanId;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.payment = clientLoan.getPayments();
        this.amount = clientLoan.getAmount();
        this.loanId = clientLoan.getLoan().getId();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getLoanId() {
        return loanId;
    }



}
