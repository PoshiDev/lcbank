package com.mindhub.homebanking.Configurations.Services.Implement;

import com.mindhub.homebanking.Configurations.Services.ClientService;
import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository repositoryClient;

    @Override
    public List<ClientDTO> getClientsDTO (){
        return repositoryClient.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public Client getClientCurrent(Authentication authentication) {
        return repositoryClient.findByMail(authentication.getName());
    }

    @Override
    public ClientDTO getClientDTO(long id) {
        return repositoryClient.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    };

    @Override
    public ClientDTO getClientDTOByMail(String mail){
        return new ClientDTO(repositoryClient.findByMail(mail));
    };

    @Override
    public void saveClient(Client client){
        repositoryClient.save(client);
    }

    @Override
    public Client getClientByMail(String mail) {
        return repositoryClient.findByMail(mail);
    };




}
