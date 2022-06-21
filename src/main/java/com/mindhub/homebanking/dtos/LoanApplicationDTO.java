package com.mindhub.homebanking.dtos;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LoanApplicationDTO {

    private String loanName;

    private int amount;

    private int payment;

    private String targetAccountNumber;

    public LoanApplicationDTO(){}


    public LoanApplicationDTO(String loanName, int amount, int payment, String targetAccountNumber) {
        this.loanName = loanName;
        this.amount = amount;
        this.payment = payment;
        this.targetAccountNumber = targetAccountNumber;
    }

    public String getLoanName() {
        return loanName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPayment() {
        return payment;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }
}
