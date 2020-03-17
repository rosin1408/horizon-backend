package br.com.horizon.service;

import br.com.horizon.model.Client;
import br.com.horizon.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService extends DefaultService<Client> {

    @Autowired
    public ClientService(ClientRepository repository) {
        super(repository);
    }

}
