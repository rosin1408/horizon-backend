package br.com.horizon.controller;

import br.com.horizon.model.Customer;
import br.com.horizon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends DefaultController<Customer> {

    @Autowired
    protected CustomerController(CustomerService service) {
        super(service);
    }
}
