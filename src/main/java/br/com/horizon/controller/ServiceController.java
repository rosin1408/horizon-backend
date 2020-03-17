package br.com.horizon.controller;

import br.com.horizon.model.Service;
import br.com.horizon.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
public class ServiceController extends DefaultController<Service> {

    @Autowired
    public ServiceController(ServiceService service) {
        super(service);
    }

}
