package com.mindhub.homebanking.Configurations.Services;


import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    public List<LoanDTO> getLoans();

    void saveClientLoan (ClientLoan clientLoan);

    public Loan findByName(String name);

}
