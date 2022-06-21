package com.mindhub.homebanking.Repository;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource     //servicio rest automatic, de la data que esta generando
public interface ClientRepository extends JpaRepository < Client, Long >{   // extends significa que le extienda los metodos
                                                                            // solo a las propiedades client y long
                                                                            // osea el padre le hereda al hijo (herencia)
                                                                            // (como es una interface solo hereda metodos, no propiedades)
                                                                            // las interfaces solo se declara las cabeceras

                                                                            //va a retornar una lista de clientes (List<Client>)
                                                                           // va a recibir por parametro un long id
    Client findByMail (String mail);
}
