package ken.vivid.auth.application.port.in.saveUser;

import ken.vivid.auth.domain.model.AuthResult;
import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;

public interface RegisterUseCase {

    AuthResult register(StoreCommand storeCommand);

    User store(StoreCommand storeCommand);
}
