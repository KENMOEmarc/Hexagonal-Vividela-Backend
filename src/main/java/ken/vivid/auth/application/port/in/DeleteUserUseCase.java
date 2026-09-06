package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.enums.Role;

public interface DeleteUserUseCase {
    // Delete user use case
    void delete(DeleteCommand deleteCommand);

    record DeleteCommand(Long targetUserId, Long actingUserId, Role actingUserRole) {
    }
}
