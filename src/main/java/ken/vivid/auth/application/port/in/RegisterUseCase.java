package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.AuthResult;
import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;

public interface RegisterUseCase {

    AuthResult register(RegisterCommand registerCommand);

    User store(StoreCommand storeCommand);

    record RegisterCommand(String firstName,
                           String lastName,
                           String userName,
                           String email,
                           String phone,
                           String rawPassword,
                           String confirmPassword) {

    }

    record StoreCommand(String firstName,
                        String lastName,
                        String userName,
                        String email,
                        String phone,
                        String rawPassword,
                        Role role){

    }
}
