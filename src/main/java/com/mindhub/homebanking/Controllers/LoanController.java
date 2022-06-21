package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.Configurations.Services.ClientService;
import com.mindhub.homebanking.Configurations.Services.Implement.AccountService;
import com.mindhub.homebanking.Configurations.Services.LoanService;
import com.mindhub.homebanking.Configurations.Services.TransactionService;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;

@RestController
@RequestMapping("/api")
public class LoanController {


    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> newLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){
        Client client = clientService.getClientByMail(authentication.getName());
        Loan loan = loanService.findByName(loanApplicationDTO.getLoanName());
        Account targetAccount = accountService.findByNumber(loanApplicationDTO.getTargetAccountNumber());

        if(loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayment() <= 0 || loanApplicationDTO.getTargetAccountNumber().isEmpty() || loanApplicationDTO.getLoanName().isEmpty() ){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if( loan == null ){
            return new ResponseEntity<>("This loan doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("Amount not available", HttpStatus.FORBIDDEN);
        }

        if(! loan.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("Payment not available", HttpStatus.FORBIDDEN);
        }

        if (targetAccount == null){
            return new ResponseEntity<>("This account doesn't exist ", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(targetAccount)){
            return new ResponseEntity<>("This account's client isn't authenticated", HttpStatus.FORBIDDEN);
        }

        Transaction loanTransaction = new Transaction(loanApplicationDTO.getAmount(), loan.getName() + "loan approved", LocalDateTime.now(), CREDIT, targetAccount, targetAccount.getBalance() + loanApplicationDTO.getAmount(), false);
        transactionService.saveTransaction(loanTransaction);

        targetAccount.setBalance( targetAccount.getBalance() + loanTransaction.getAmount());
        accountService.saveAccount(targetAccount);

        ClientLoan clientLoan = new ClientLoan((int) (loanApplicationDTO.getAmount() * loan.getPrjTaxes()), loanApplicationDTO.getPayment(), client, loan );
        loanService.saveClientLoan(clientLoan);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
