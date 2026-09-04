package ken.vivid.auth.adapter.in.web.payloads;

import ken.vivid.auth.domain.model.AuthResult;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private long expiresIn;
    private Long userId;
    private String email;
    private String role;

    public static LoginResponse from(AuthResult result) {
        return LoginResponse.builder()
                .token(result.token())
                .expiresIn(result.expiresInMillis())
                .userId(result.userId())
                .email(result.email())
                .role(result.role().name())
                .build();
    }
}
