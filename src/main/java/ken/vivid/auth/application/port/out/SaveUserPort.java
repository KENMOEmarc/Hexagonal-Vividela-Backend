package ken.vivid.auth.application.port.out;

import ken.vivid.auth.domain.model.User;

public interface SaveUserPort {
    User save(User user);
}