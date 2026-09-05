package ken.vivid.auth.application.port.in;

import ken.vivid.auth.domain.model.User;

public interface ChangePasswordUseCase {
    User changePassword(ChangePasswordCommand command);

    record ChangePasswordCommand(
            Long userId,
            String currentPassword,
            String newPassword,
            String confirmPassword
    ) {
    }
}
