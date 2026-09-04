package ken.vivid.auth.adapter.out.blacklist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenJpaRepository extends JpaRepository<RevokedTokenJpaEntity, Long> {
    boolean existsByToken(String token);
}
