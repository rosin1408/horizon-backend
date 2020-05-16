package br.com.horizon.controller;

import br.com.horizon.dto.ApiResponse;
import br.com.horizon.model.DefaultEntity;
import br.com.horizon.service.DefaultService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class DefaultController<T extends DefaultEntity> {

    private DefaultService<T> service;

    protected DefaultController(DefaultService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody T entity) {
        this.service.save(entity);

        return ResponseEntity.of(Optional.of(new ApiResponse(true, "Success")));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody T entity) {
        if (entity.getId() == null) {
            return ResponseEntity.of(Optional.of(new ApiResponse(false, "Id is required to update")));
        }
        this.service.save(entity);

        return ResponseEntity.of(Optional.of(new ApiResponse(true, "Success")));
    }

    @GetMapping
    public Page<T> list(int page, int size) {
        return this.service.all(PageRequest.of(page, size    ));
    }

    @GetMapping("/{id}")
    public T findById(@PathVariable Long id) {
        return this.service.findOne(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        this.service.delete(id);

        return ResponseEntity.of(Optional.of(new ApiResponse(true, "Deleted")));
    }
}
