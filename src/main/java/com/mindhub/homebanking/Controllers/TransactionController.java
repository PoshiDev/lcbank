package com.mindhub.homebanking.Controllers;


import com.mindhub.homebanking.Configurations.Services.ClientService;
import com.mindhub.homebanking.Configurations.Services.Implement.AccountService;
import com.mindhub.homebanking.Configurations.Services.TransactionService;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.Repository.TransactionRepository;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;
import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.ThreadContext.isEmpty;

@RestController
@RequestMapping("/api")
public class TransactionController {

    //@Autowired
    //private TransactionRepository repositoryTransaction;

    //@Autowired
    //private AccountRepository repositoryAccount;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    ///@Autowired
   //private ClientRepository repositoryClient;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactionDTO() {
        return transactionService.getTransactionDTO();
        //return repositoryTransaction.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(toList());

    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransactionDTO(@PathVariable Long id) {
        return transactionService.getTransaction(id);
        //return repositoryTransaction.findById(id).map(TransactionDTO::new).orElse(null);
    }



    @Transactional
    @PostMapping("/clients/current/transactions")
    public ResponseEntity<Object> newTransaction(Authentication authentication, @RequestParam double amount,
     @RequestParam String description, @RequestParam String originAccountNumber,@RequestParam String targetAccountNumber){

        LocalDateTime date = LocalDateTime.now();
        Client client = clientService.getClientByMail(authentication.getName());
        //Client client = repositoryClient.findByMail(authentication.getName());
        Account originAccount = accountService.findByNumber(originAccountNumber);
        Account targetAccount = accountService.findByNumber(targetAccountNumber);
        //Account originAccount = repositoryAccount.findByNumber(originAccountNumber);
        //Account targetAccount = repositoryAccount.findByNumber(targetAccountNumber);

        if (amount <=0.00  || description.isEmpty() || originAccountNumber.isEmpty() || targetAccountNumber.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if (originAccountNumber.equals( targetAccountNumber)) {

            return new ResponseEntity<>("Origin account and target account can't be the same", HttpStatus.FORBIDDEN);

        }
        if (originAccount == null) {

            return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);

        }

        if (! client.getAccounts().contains(originAccount) ){

            return new ResponseEntity<>("Account owner is not authenticated", HttpStatus.FORBIDDEN);

        }

        if (targetAccount == null) {

            return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);

        }
        if (originAccount.getBalance() - new Transaction().getAmount() < 0.00 ) {

            return new ResponseEntity<>("Your balance isn't enough for this transaction", HttpStatus.FORBIDDEN);

        }


        Transaction sendTransaction = new Transaction( amount , description, date, DEBIT, originAccount, originAccount.getBalance() - amount, false);
        Transaction receivedTransaction = new Transaction( amount, description, date, CREDIT, targetAccount, targetAccount.getBalance() + amount, false);
        transactionService.saveTransaction(sendTransaction);
        transactionService.saveTransaction(receivedTransaction);
        //repositoryTransaction.save(sendTransaction);
        //repositoryTransaction.save(receivedTransaction);

        originAccount.setBalance( originAccount.getBalance() - sendTransaction.getAmount());
        targetAccount.setBalance( targetAccount.getBalance() + receivedTransaction.getAmount());
        accountService.saveAccount(originAccount);
        accountService.saveAccount(targetAccount);
        //repositoryAccount.save(originAccount);
        //repositoryAccount.save(targetAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
