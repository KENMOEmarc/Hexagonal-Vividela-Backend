package ken.vivid.auth.application.port.in.updateUser;

import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;

public interface UpdateUserUseCase {
    User update(UpdateCommand updateCommand);
}
