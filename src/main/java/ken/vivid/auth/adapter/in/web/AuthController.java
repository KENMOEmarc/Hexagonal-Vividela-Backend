package ken.vivid.auth.adapter.in.web;

import jakarta.validation.Valid;
import ken.vivid.auth.adapter.in.web.dto.UserDto;
import ken.vivid.auth.adapter.in.web.payloads.LoginRequest;
import ken.vivid.auth.adapter.in.web.payloads.LoginResponse;
import ken.vivid.auth.adapter.in.web.payloads.RegisterRequest;
import ken.vivid.auth.application.port.in.LoginUseCase;
import ken.vivid.auth.application.port.in.LogoutUseCase;
import ken.vivid.auth.application.port.in.RegisterUseCase;
import ken.vivid.auth.domain.model.AuthResult;
import ken.vivid.auth.domain.model.User;
import ken.vivid.shared.adapter.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResult result = loginUseCase.login(
                new LoginUseCase.LoginCommand(request.getIdentifier(), request.getPassword())
        );
        return ResponseEntity.ok(ApiResponse.success("Connexion réussie", LoginResponse.from(result)));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest request) {
        User user = registerUseCase.register(new RegisterUseCase.RegisterCommand(
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhone()
        ));
        return ResponseEntity.ok(ApiResponse.success("Compte créé avec succès", UserDto.from(user)));
    }

    @PostMapping("/store")
    public ResponseEntity<ApiResponse<UserDto>> store(@Valid @RequestBody RegisterRequest request) {
        User user = registerUseCase.store(new RegisterUseCase.StoreCommand(
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhone(),
                request.getRole()
        ));
        return ResponseEntity.ok(ApiResponse.success("Compte créé avec succès", UserDto.from(user)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        logoutUseCase.logout(token);
        return ResponseEntity.ok(ApiResponse.success("Déconnexion réussie"));
    }
}
