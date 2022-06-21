package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity //genera una tabla en la base de datos
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String nombre, apellido, mail, password; // hice mis propiedades del objeto

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set <Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set <Card> cards = new HashSet<>();

    public Client (){}              // constructor vacio para despues crear la cantidad
                                            //de constructores que yo quiera. Para usarlo en algun momento (no se cuando)
                                            // constructor de nombre, es una propiedad vacia
    public Client(String nombre, String apellido, String mail, String password) {   //parametros de mi constructor con las 4this.id = id;                                                          // propiedades que yo le digo
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    /* public void setId(long id) { No me hace falta generar el set del id porque ya
                                    lo estoy generando automaticamente con mi
          this.id = id;                          generate value y my generate generator
    }*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Account> getAccounts(){
        return accounts;
    }

    public void addAccount (Account account){
        account.setClient(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public List<Loan> getLoan (){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard (Card card){
        card.setClient(this);
        cards.add(card);
    }

    public String getFullName(){
        return nombre + " " + apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
