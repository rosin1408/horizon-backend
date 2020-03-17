package br.com.horizon.repository;

import br.com.horizon.model.DefaultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DefaultRepository<T extends DefaultEntity> extends JpaRepository<T, Long> {

}
