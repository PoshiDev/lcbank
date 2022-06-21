package com.mindhub.homebanking.Repository;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long > {
    //Card findByMail(String name);

    //List<Card> findByMail(String mail);
}
