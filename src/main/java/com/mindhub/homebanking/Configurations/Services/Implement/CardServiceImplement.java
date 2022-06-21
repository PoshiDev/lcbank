package com.mindhub.homebanking.Configurations.Services.Implement;

import com.mindhub.homebanking.Configurations.Services.CardService;
import com.mindhub.homebanking.Repository.CardRepository;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    CardRepository repositoryCard;

    @Override
    public List<CardDTO> getCards() {
        return repositoryCard.findAll().stream().map(card -> new CardDTO(card)).collect(toList());
    }

    @Override
    public void saveCard(Card card) {
        repositoryCard.save(card);
    }

    @Override
    public Card findById(long id) {
        return repositoryCard.findById(id).orElse(null);
    }


}
