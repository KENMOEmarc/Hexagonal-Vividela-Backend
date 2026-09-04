package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.AuthResult;

public interface LoginUseCase {

    AuthResult login(LoginCommand loginCommand);

    record LoginCommand(String email, String rawPassword) {

    }
}
