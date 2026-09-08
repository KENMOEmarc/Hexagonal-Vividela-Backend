package ken.vivid.auth.application.port.out;

import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;

import java.util.Optional;

public interface LoadUserPort {

    Optional<User> loadByEmailOrUserName(String identifier);

    Optional<User> loadById(Long id);

    Optional<User> loadByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    long countByRole(Role role);
}