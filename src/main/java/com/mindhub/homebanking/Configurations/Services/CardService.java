package com.mindhub.homebanking.Configurations.Services;


import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    public List<CardDTO> getCards();

    void saveCard (Card card);

    public Card findById(long id);

}
