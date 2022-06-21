package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ClientDTO {

    public ClientDTO (){}

    private String nombre, apellido, mail;
    private long id;

    //AccountDTO accountDTO;

    // @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    private Set<AccountDTO> accounts = new HashSet<>();

    private Set<ClientLoanDTO> loans = new HashSet<>();

    private Set <CardDTO> cards = new HashSet<>();

    public ClientDTO(Client client) {

        this.id = client.getId();

        this.nombre = client.getNombre();

        this.apellido = client.getApellido();

        this.mail = client.getMail();

        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());

        this.loans = client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());

        this.cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }

    public String getNombre() {
        return nombre;
    }


    public String getApellido() {
        return apellido;
    }


    public String getMail() {
        return mail;
    }


    public long getId() {
        return id;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }
}
