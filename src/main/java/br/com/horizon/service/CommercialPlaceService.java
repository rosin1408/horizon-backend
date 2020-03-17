package br.com.horizon.service;

import br.com.horizon.model.CommercialPlace;
import br.com.horizon.repository.CommercialPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommercialPlaceService extends DefaultService<CommercialPlace> {

    @Autowired
    public CommercialPlaceService(CommercialPlaceRepository repository) {
        super(repository);
    }
}
