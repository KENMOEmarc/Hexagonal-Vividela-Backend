package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.User;

public interface UpdateUserUseCase {
    //Update User use case
    User update(UpdateCommand updateCommand);

    record UpdateCommand(Long userId,
                         String firstName,
                         String lastName,
                         String userName,
                         String email,
                         String phone) {

    }
}
