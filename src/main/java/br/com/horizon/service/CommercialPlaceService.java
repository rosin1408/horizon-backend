package br.com.horizon.service;

import br.com.horizon.model.CommercialPlace;
import br.com.horizon.repository.CommercialPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommercialPlaceService {

    @Autowired
    private CommercialPlaceRepository repository;

    public CommercialPlace save(CommercialPlace commercialPlace) {
        return repository.save(commercialPlace);
    }

    public Page<CommercialPlace> all(Pageable page) {
        return repository.findAll(page);
    }

    public CommercialPlace findOne(Long id) {
        return repository.findById(id).get();
    }

    public void delete(Long id) {
        var commercialPlace = findOne(id);
        repository.delete(commercialPlace);
    }
}
