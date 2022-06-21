package com.mindhub.homebanking.Configurations.Services;


import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {

    public List<TransactionDTO> getTransactionDTO();

    public List<Transaction> getTransactions();

    public TransactionDTO getTransaction(Long id);

    void saveTransaction(Transaction transaction);

}
