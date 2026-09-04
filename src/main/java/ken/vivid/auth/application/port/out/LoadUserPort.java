package ken.vivid.auth.application.port.out;

import ken.vivid.auth.domain.model.User;

import java.util.Optional;

public interface LoadUserPort {

    Optional<User> loadByEmail(String email);

    Optional<User> loadById(Long id);

    boolean existsByEmail(String email);
}