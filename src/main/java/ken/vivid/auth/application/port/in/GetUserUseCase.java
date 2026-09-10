package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.User;

import java.util.List;

public interface GetUserUseCase {
    List<User> findAll();

    User find(Long id);
}
