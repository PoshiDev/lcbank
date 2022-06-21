package com.mindhub.homebanking.Configurations.Services.Implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {

    public List<AccountDTO> getAccounts();

    public AccountDTO getAccountDTO(Long id);

    //public List <AccountDTO> getCurrentClientAccounts();

    public Account getAccount(Long id);

    void saveAccount(Account account);

    public Account findByNumber(String number);

}
