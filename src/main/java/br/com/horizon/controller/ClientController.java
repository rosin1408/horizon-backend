package br.com.horizon.controller;

import br.com.horizon.model.Client;
import br.com.horizon.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientController extends DefaultController<Client> {

    @Autowired
    protected ClientController(ClientService service) {
        super(service);
    }
}
