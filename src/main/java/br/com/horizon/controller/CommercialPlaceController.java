package br.com.horizon.controller;

import br.com.horizon.model.CommercialPlace;
import br.com.horizon.service.CommercialPlaceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commercialplace")
public class CommercialPlaceController extends DefaultController<CommercialPlace> {


    protected CommercialPlaceController(CommercialPlaceService service) {
        super(service);
    }
}
