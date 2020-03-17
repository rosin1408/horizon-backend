package br.com.horizon.service;


import br.com.horizon.model.Service;
import br.com.horizon.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService extends DefaultService<Service> {

    @Autowired
    protected ServiceService(ServiceRepository repository) {
        super(repository);
    }

}
