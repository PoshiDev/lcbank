package com.mindhub.homebanking.Configurations.Services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface ClientService {    //interface: con lo que interactuo para que genere una accion

    public List<ClientDTO> getClientsDTO ();

    Client getClientCurrent(Authentication authentication);

    ClientDTO getClientDTO(long id);

    ClientDTO getClientDTOByMail(String mail);

    void saveClient(Client client);

    Client getClientByMail(String mail);


}
