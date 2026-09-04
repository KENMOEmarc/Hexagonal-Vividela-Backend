package ken.vivid.auth.adapter.out.blacklist;

import ken.vivid.auth.application.port.out.TokenBlacklistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MySqlTokenBlacklistAdapter implements TokenBlacklistPort {

    private final RevokedTokenJpaRepository repository;

    @Override
    public void revoke(String token) {
        if (!repository.existsByToken(token)) {
            repository.save(new RevokedTokenJpaEntity(token));
        }
    }

    @Override
    public boolean isRevoked(String token) {
        return repository.existsByToken(token);
    }
}
