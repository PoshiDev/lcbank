package com.mindhub.homebanking.Controllers;


import com.mindhub.homebanking.Configurations.Services.CardService;
import com.mindhub.homebanking.Configurations.Services.ClientService;
import com.mindhub.homebanking.Repository.CardRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ColorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.Utils.Utils.randomNumber;
import static com.mindhub.homebanking.Utils.UtilsInt.randomNumberInt;
import static com.mindhub.homebanking.models.CardType.CREDIT;
import static com.mindhub.homebanking.models.CardType.DEBIT;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    //@Autowired
    //private CardRepository repositoryCard;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;


    //@Autowired
    //private ClientRepository repositoryClient;

    @GetMapping("/cards")
    public List<CardDTO> getCards() {
        return cardService.getCards();
        //return repositoryCard.findAll().stream().map(card -> new CardDTO(card)).collect(toList());
    }

    @GetMapping("/clients/current/cards")
    public List <CardDTO> getCurrentClientCards(Authentication authentication){
        Client currentClient = clientService.getClientByMail(authentication.getName());
        // Client currentClient = repositoryClient.findByMail(authentication.getName());
        return currentClient.getCards().stream().map(card -> new CardDTO(card)).collect(toList());
    }


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication, @RequestParam CardType cardType, @RequestParam ColorType colorType){
        Client client = clientService.getClientByMail(authentication.getName());
        // Client client = repositoryClient.findByMail(authentication.getName());
        String number = "4" + randomNumberInt(100,999) + " " + randomNumberInt(1000,9999) + " " + randomNumberInt(1000,9999) + " " + randomNumberInt(1000,9999);
        // long number = randomNumber(0,9999) + randomNumber(0,9999) + randomNumber(0,9999) + randomNumber(0,9999);
        int cvv = randomNumberInt(100,999);
        LocalDate fromDate = LocalDate.now();
        LocalDate thruDate = LocalDate.now().plusYears(5);
        String cardholder = client.getFullName();
        List <Card> debitCard = client.getCards().stream().filter(debit -> debit.getCardType() == DEBIT && !debit.isDisable()).collect(toList());
        List <Card> creditCard = client.getCards().stream().filter(credit -> credit.getCardType() == CREDIT && !credit.isDisable()).collect(toList());
        if(cardType == DEBIT && debitCard.size() >= 3){
            return new ResponseEntity<>("Max amount of debit card reached", HttpStatus.FORBIDDEN);
        }
        if(cardType == CREDIT && creditCard.size() >= 3){
            return new ResponseEntity<>("Max amount of credit card reached", HttpStatus.FORBIDDEN);
        }
        cardService.saveCard(new Card(cardType, colorType, number, cvv, fromDate, thruDate, client, false ));
        //repositoryCard.save(new Card(cardType, colorType, number, cvv, fromDate, thruDate, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> disableCard (Authentication authentication, @RequestParam long id){
        Card card = cardService.findById(id);
        Client client = clientService.getClientByMail(authentication.getName());
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("This is not your card", HttpStatus.FORBIDDEN);
        }
        card.setDisable(true);
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
