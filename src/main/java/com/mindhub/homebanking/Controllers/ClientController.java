package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Configurations.Services.ClientService;
import com.mindhub.homebanking.Configurations.Services.Implement.AccountService;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.Utils.Utils.randomNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    //@Autowired
    //private ClientRepository repositoryClient;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    //@Autowired
    //private AccountRepository repositoryAccount;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTO(id);
        //return repositoryClient.findById(id).map(ClientDTO::new).orElse(null);
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return clientService.getClientDTOByMail(authentication.getName());
        //return new ClientDTO(repositoryClient.findByMail(authentication.getName()));
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String mail, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || mail.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByMail(mail) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client nuevoCliente = new Client(firstName, lastName, mail, passwordEncoder.encode(password));
        clientService.saveClient(nuevoCliente);
        //repositoryClient.save(nuevoCliente);

        Account account = new Account("VIN-" + randomNumber(10000000,99999999), LocalDateTime.now(), 0.00, nuevoCliente, false, AccountType.CURRENT );
        accountService.saveAccount(account);
        //repositoryAccount.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }



}
