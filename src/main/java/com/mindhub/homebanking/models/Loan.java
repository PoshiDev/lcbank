package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;

    private int maxAmount;

    private double prjTaxes;

    @ElementCollection
    @Column(name="payments")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan (){}


    public Loan(String name, int maxAmount, List<Integer> payments, double prjTaxes){
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.prjTaxes = prjTaxes;
    }

    public double getPrjTaxes() {
        return prjTaxes;
    }

    public void setPrjTaxes(double prjTaxes) {
        this.prjTaxes = prjTaxes;
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

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }

    public List<Client> getClients (){
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
    }

}
