package br.com.horizon.repository;

import br.com.horizon.model.CommercialPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialPlaceRepository extends JpaRepository<CommercialPlace, Long> {

}
