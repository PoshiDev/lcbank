package com.mindhub.homebanking.Configurations.Services.Implement;


import com.mindhub.homebanking.Configurations.Services.TransactionService;
import com.mindhub.homebanking.Repository.TransactionRepository;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    TransactionRepository repositoryTransaction;

    @Override
    public List<TransactionDTO> getTransactionDTO() {
        return repositoryTransaction.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(toList());
    }

    @Override
    public List<Transaction> getTransactions() {
        return repositoryTransaction.findAll();
    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        return repositoryTransaction.findById(id).map(TransactionDTO::new).orElse(null);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        repositoryTransaction.save(transaction);
    }
}
