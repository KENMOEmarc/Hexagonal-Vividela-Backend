package ken.vivid.auth.application.port.in.updateUser;

import ken.vivid.auth.domain.model.enums.Role;

public record UpdateCommand(Long targetUserId,
                            Long actingUserId,
                            Role actingUserRole,
                            String firstName,
                            String lastName,
                            String userName,
                            String email,
                            String phone) {

}
