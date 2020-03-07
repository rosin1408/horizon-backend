package br.com.horizon.service;


import br.com.horizon.model.Service;
import br.com.horizon.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    public Service save(Service service) {
        return repository.save(service);
    }

    public Page<Service> all(Pageable page) {
        return repository.findAll(page);
    }

    public Service findOne(Long id) {
        return repository.findById(id).get();
    }

    public void delete(Long id) {
        var service = findOne(id);
        repository.delete(service);
    }
}
