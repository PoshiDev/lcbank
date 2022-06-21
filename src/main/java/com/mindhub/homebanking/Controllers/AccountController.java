package com.mindhub.homebanking.Controllers;


import com.mindhub.homebanking.Configurations.Services.ClientService;
import com.mindhub.homebanking.Configurations.Services.Implement.AccountService;
import com.mindhub.homebanking.Configurations.Services.TransactionService;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.Utils.Utils.randomNumber;
import static com.mindhub.homebanking.models.AccountType.CURRENT;
import static com.mindhub.homebanking.models.AccountType.SAVING;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

   // @Autowired
    //private AccountRepository repositoryAccount;

    //@Autowired
    //private ClientRepository repositoryClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
        //return repositoryAccount.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccountDTO(id);
        //return repositoryAccount.findById(id).map(AccountDTO::new).orElse(null);
    }

    @GetMapping("/clients/current/accounts")
    public List <AccountDTO> getCurrentClientAccounts(Authentication authentication){
        Client currentClient = clientService.getClientByMail(authentication.getName());
        //Client currentClient = repositoryClient.findByMail(authentication.getName());
        return currentClient.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam AccountType accountType){
        LocalDateTime creationDate = LocalDateTime.now();
        double balance = 0.00;
        String number = "VIN-" + randomNumber(10000000,99999999);
        Client client = clientService.getClientByMail(authentication.getName());
        List <Account> activeAccounts = client.getAccounts().stream().filter(account -> !account.isDisableAccount()).collect(toList());

        if(activeAccounts.size() >= 3){
            return new ResponseEntity<>("Max amount of accounts reached", HttpStatus.FORBIDDEN);
        }

        accountService.saveAccount(new Account(number, creationDate, balance, client, false, accountType));
        //repositoryAccount.save(new Account(number, creationDate, balance, client));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> disableAccount(Authentication authentication, @RequestParam long id){
        Account account = accountService.getAccount(id);
        Client client = clientService.getClientByMail(authentication.getName());

        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("This is not your account", HttpStatus.FORBIDDEN);
        }
        account.getTransactions().forEach(transaction -> {
            transaction.setDisableTransactions(true);
            transactionService.saveTransaction(transaction);
        });
        account.setDisableAccount(true);
        accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }






}
