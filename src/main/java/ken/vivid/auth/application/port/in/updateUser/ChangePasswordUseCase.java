package ken.vivid.auth.application.port.in.updateUser;

import ken.vivid.auth.domain.model.User;

public interface ChangePasswordUseCase {
    User changePassword(ChangePasswordCommand command);
}
