package ken.vivid.auth.adapter.out.persistence;

import ken.vivid.auth.application.port.out.LoadUserPort;
import ken.vivid.auth.application.port.out.SaveUserPort;
import ken.vivid.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, SaveUserPort {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public Optional<User> loadByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> loadById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserJpaEntity saved = jpaRepository.save(mapper.toEntity(user));
        return mapper.toDomain(saved);
    }
}
