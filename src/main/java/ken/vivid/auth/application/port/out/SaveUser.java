package ken.vivid.auth.application.port.out;

import ken.vivid.auth.domain.model.User;

public interface SaveUser {
    User save(User user);
}