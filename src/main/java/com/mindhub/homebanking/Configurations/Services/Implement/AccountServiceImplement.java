package com.mindhub.homebanking.Configurations.Services.Implement;

import com.mindhub.homebanking.Configurations.Services.ClientService;
import com.mindhub.homebanking.Repository.AccountRepository;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mindhub.homebanking.Configurations.Services.Implement.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository repositoryAccount;

    @Override
    public List<AccountDTO> getAccounts() {
        return repositoryAccount.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountDTO(Long id) {
        return new AccountDTO(repositoryAccount.findById(id).orElse(null));
        //return repositoryAccount.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public Account getAccount(Long id){
        return repositoryAccount.findById(id).orElse(null);
    }


    @Override
    public void saveAccount(Account account) {
        repositoryAccount.save(account);
    }

    @Override
    public Account findByNumber(String number) {
        return repositoryAccount.findByNumber(number);
    }


}
