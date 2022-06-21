package com.mindhub.homebanking.Repository;


import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository <Account, Long> {

    //Account findById(long id);
    Account findByNumber(String number);

}
