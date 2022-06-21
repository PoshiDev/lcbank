package com.mindhub.homebanking.Configurations;

import com.mindhub.homebanking.Repository.ClientRepository;
import com.mindhub.homebanking.models.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAutentication  extends GlobalAuthenticationConfigurerAdapter {

    @Autowired

    ClientRepository repositoryClient;

    @Override

    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {

            Client client = repositoryClient.findByMail(inputName); //decia inputName

            if (client != null) {

                String userMail = client.getMail();

                if (userMail.contains("@bank.com")){

                    return new User(client.getMail(), client.getPassword(), AuthorityUtils.createAuthorityList("ADMIN"));

                }else {

                    return new User(client.getMail(), client.getPassword(), AuthorityUtils.createAuthorityList("CLIENT"));
                }
            } else {

                throw new UsernameNotFoundException("Unknown client: " + inputName);

            }
        });

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

