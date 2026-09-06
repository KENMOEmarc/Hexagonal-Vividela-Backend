package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;

public interface UpdateUserUseCase {
    //Update User use case
    User update(UpdateCommand updateCommand);

    record UpdateCommand(Long targetUserId,
                         Long actingUserId,
                         Role actingUserRole,
                         String firstName,
                         String lastName,
                         String userName,
                         String email,
                         String phone) {

    }
}
