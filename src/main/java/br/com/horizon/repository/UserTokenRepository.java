package br.com.horizon.repository;

import br.com.horizon.model.UserToken;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    @Query("select u from UserToken u where u.uuid = ?1 and u.validAt > ?2")
    Optional<UserToken> findUserTokenByUuid(String uuid, LocalDateTime now);
}
