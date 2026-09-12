package ken.vivid.auth.application.port.in.deleteUser;

import ken.vivid.auth.domain.model.enums.Role;

public interface DeleteUserUseCase {
    // Delete user use case
    void delete(DeleteCommand deleteCommand);

    //TODO Create a command repository to list all Command
    record DeleteCommand(Long targetUserId, Long actingUserId, Role actingUserRole) {
    }
}
