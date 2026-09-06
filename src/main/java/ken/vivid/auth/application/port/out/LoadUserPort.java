package ken.vivid.auth.application.port.out;

import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;

import java.util.Optional;

public interface LoadUserPort {

    Optional<User> loadByEmail(String email);

    Optional<User> loadById(Long id);

    Optional<User> loadByIdentifier(String identifier);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    long countByRole(Role role);
}