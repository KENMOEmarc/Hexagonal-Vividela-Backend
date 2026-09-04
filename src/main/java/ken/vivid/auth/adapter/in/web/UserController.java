package ken.vivid.auth.adapter.in.web;

import ken.vivid.auth.adapter.in.web.dto.UserDto;
import ken.vivid.auth.application.port.in.GetCurrentUserUseCase;
import ken.vivid.shared.adapter.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GetCurrentUserUseCase getCurrentUserUseCase;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(Authentication authentication) {
        var user = getCurrentUserUseCase.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Profil utilisateur", UserDto.from(user)));
    }
}
