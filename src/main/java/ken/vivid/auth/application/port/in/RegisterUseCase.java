package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.User;

public interface RegisterUseCase {

    User register(RegisterCommand registerCommand);

    User store(StoreCommand storeCommand);

    record RegisterCommand(String firstName, String lastName, String userName, String email, String phone, String rawPassword) {

    }

    record StoreCommand(String firstName, String lastName, String userName, String email, String phone, String rawPassword, String confirmPassword){

    }
}
