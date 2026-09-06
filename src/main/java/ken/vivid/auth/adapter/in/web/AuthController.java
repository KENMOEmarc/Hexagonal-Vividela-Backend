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
import ken.vivid.auth.domain.model.enums.Role;
import ken.vivid.shared.adapter.web.ApiResponse;
import ken.vivid.shared.domain.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String ROLE_PREFIX = "ROLE_";

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
                request.getFirstName(),
                request.getLastName(),
                request.getUserName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword(),
                request.getConfirmPassword()
        ));
        return ResponseEntity.ok(ApiResponse.success("Compte créé avec succès", UserDto.from(user)));
    }

    /**
     * Staff-only account creation (can set an arbitrary role). Unlike
     * /register, this must never be left reachable by any authenticated
     * user — @PreAuthorize plus the RoleHierarchyService check inside
     * AuthService.store() enforce that only ADMIN/MANAGER can call it, and
     * that a MANAGER can't mint another ADMIN.
     */
    @PostMapping("/store")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ApiResponse<UserDto>> store(@Valid @RequestBody RegisterRequest request,
                                                      Authentication authentication) {
        User user = registerUseCase.store(new RegisterUseCase.StoreCommand(
                request.getFirstName(),
                request.getLastName(),
                request.getUserName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword(),
                parseRole(request.getRole())
        ));
        return ResponseEntity.ok(ApiResponse.success("Compte créé avec succès", UserDto.from(user)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        logoutUseCase.logout(token);
        return ResponseEntity.ok(ApiResponse.success("Déconnexion réussie"));
    }

    private Role parseRole(String role) {
        if (role == null || role.isBlank()) {
            throw new InvalidRequestException("Le rôle est obligatoire pour la création d'un compte via cet endpoint");
        }
        try {
            return Role.valueOf(role.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Rôle invalide : " + role);
        }
    }

    private Role extractRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith(ROLE_PREFIX))
                .map(authority -> authority.substring(ROLE_PREFIX.length()))
                .map(Role::valueOf)
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("Rôle de l'utilisateur courant introuvable"));
    }
}
