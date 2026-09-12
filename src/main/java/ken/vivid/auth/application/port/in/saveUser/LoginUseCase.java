package ken.vivid.auth.application.port.in.saveUser;

import ken.vivid.auth.domain.model.AuthResult;

public interface LoginUseCase {

    AuthResult login(LoginCommand loginCommand);
}
