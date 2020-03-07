package br.com.horizon.controller;

import br.com.horizon.dto.ApiResponse;
import br.com.horizon.model.Service;
import br.com.horizon.service.ServiceService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    private ServiceService service;

    @PostMapping
    public ResponseEntity<ApiResponse> createService(@RequestBody Service serviceEntity) {
        this.service.save(serviceEntity);

        return ResponseEntity.of(Optional.of(new ApiResponse(true, "Service successfully created")));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateService(@RequestBody Service serviceEntity) {
        if (serviceEntity.getId() == null) {
            return ResponseEntity.of(Optional.of(new ApiResponse(false, "Id is required to update service")));
        }
        this.service.save(serviceEntity);

        return ResponseEntity.of(Optional.of(new ApiResponse(true, "Service successfully created")));
    }

    @GetMapping
    public Page<Service> listServices(int page, int size) {
        return this.service.all(PageRequest.of(page -1, size    ));
    }

    @GetMapping("/{id}")
    public Service findById(@PathVariable Long id) {
        return this.service.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        this.service.delete(id);

        return ResponseEntity.of(Optional.of(new ApiResponse(true, "Service successfully deleted")));
    }

}
