package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.User;

public interface GetCurrentUserUseCase {
    User getCurrentUser(String email);
}
