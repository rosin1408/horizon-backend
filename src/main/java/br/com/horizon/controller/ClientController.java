package br.com.horizon.controller;

import br.com.horizon.dto.ApiResponse;
import br.com.horizon.model.Client;
import br.com.horizon.service.ClientService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createClient(Client client) {
        service.save(client);

        return ResponseEntity.of(Optional.of(new ApiResponse(true, "Client sucessfull created")));
    }
}
