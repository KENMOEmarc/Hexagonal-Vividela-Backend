package ken.vivid.auth.application.port.in.saveUser;

import ken.vivid.auth.domain.model.enums.Role;

public record StoreCommand(Long id, String firstName,
                           String lastName,
                           String userName,
                           String email,
                           String phone,
                           String rawPassword,
                           Role role){

}