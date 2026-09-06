package ken.vivid.auth.adapter.out.blacklist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RevokedTokenJpaRepository extends JpaRepository<RevokedTokenJpaEntity, Long> {
    boolean existsByToken(String token);

    long deleteByRevokedAtBefore(LocalDateTime cutoff);
}
