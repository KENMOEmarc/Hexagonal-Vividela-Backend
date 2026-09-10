package ken.vivid.auth.domain.model;

import ken.vivid.auth.domain.model.enums.Role;

public record AuthResult(String token,
                         long expiresInMillis,
                        User user) {
}
