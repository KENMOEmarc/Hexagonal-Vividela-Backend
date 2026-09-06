package ken.vivid.auth.adapter.out.persistence;

import ken.vivid.auth.domain.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserJpaEntity> findByEmailOrUserName(String identifier, String identifier1);

    boolean existsByUserName(String userName);

    long countByRole(Role role);
}

