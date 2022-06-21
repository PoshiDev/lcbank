package com.mindhub.homebanking.Configurations.Services.Implement;

import com.mindhub.homebanking.Configurations.Services.LoanService;
import com.mindhub.homebanking.Repository.ClientLoanRepository;
import com.mindhub.homebanking.Repository.LoanRepository;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository repositoryLoan;

    @Autowired
    private ClientLoanRepository repositoryClientLoan;

    @Override
    public List<LoanDTO> getLoans() {
        return repositoryLoan.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        repositoryClientLoan.save(clientLoan);
    }

    @Override
    public Loan findByName(String name) {
        return repositoryLoan.findByName(name);
    }
}
