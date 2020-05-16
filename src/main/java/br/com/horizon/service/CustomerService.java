package br.com.horizon.service;

import br.com.horizon.model.Customer;
import br.com.horizon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends DefaultService<Customer> {

    @Autowired
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }

}
