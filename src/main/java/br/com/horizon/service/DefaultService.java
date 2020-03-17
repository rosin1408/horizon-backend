package br.com.horizon.service;

import br.com.horizon.model.DefaultEntity;
import br.com.horizon.repository.DefaultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class DefaultService<T extends DefaultEntity> {

    private DefaultRepository<T> repository;

    protected DefaultService(DefaultRepository<T> repository) {
        this.repository = repository;
    }

    protected DefaultRepository<T> getRepository() {
        return this.repository;
    }

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public Page<T> all(Pageable page) {
        return repository.findAll(page);
    }

    public T findOne(Long id) {
        return repository.findById(id).get();
    }

    public void delete(Long id) {
        var commercialPlace = findOne(id);
        repository.delete(commercialPlace);
    }
}
