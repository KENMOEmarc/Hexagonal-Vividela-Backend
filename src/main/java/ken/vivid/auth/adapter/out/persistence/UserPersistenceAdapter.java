package ken.vivid.auth.adapter.out.persistence;

import ken.vivid.auth.application.port.out.DeleteUser;
import ken.vivid.auth.application.port.out.LoadUser;
import ken.vivid.auth.application.port.out.SaveUser;
import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUser, SaveUser, DeleteUser {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public Optional<User> loadByEmailOrUserName(String identifier) {
        return jpaRepository.findByEmailOrUserName(identifier).map(mapper::toDomain);
    }

    @Override
    public Optional<User> loadById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> loadByUserName(String userName) {
        return jpaRepository.findByUserName(userName).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return jpaRepository.existsByUserName(userName);
    }

    @Override
    public long countByRole(Role role) {
        return jpaRepository.countByRole(role);
    }

    @Override
    public User save(User user) {
        UserJpaEntity saved = jpaRepository.save(mapper.toEntity(user));
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}
