package ken.vivid.auth.adapter.in.web;

import jakarta.validation.Valid;
import ken.vivid.auth.adapter.in.web.dto.UserDto;
import ken.vivid.auth.adapter.in.web.payloads.ChangePasswordRequest;
import ken.vivid.auth.adapter.in.web.payloads.UpdateUserRequest;
import ken.vivid.auth.adapter.out.persistence.UserMapper;
import ken.vivid.auth.application.port.in.*;
import ken.vivid.auth.domain.model.User;
import ken.vivid.shared.adapter.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser(Authentication authentication) {
        var user = getCurrentUserUseCase.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("User profile", UserDto.from(user)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        User user = getUserUseCase.find(id);
        return ResponseEntity.ok(ApiResponse.success("User found", UserDto.from(user)));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers () {
        List<User> users = getUserUseCase.findAll();
        List<UserDto> userDtos = users.stream().map(UserDto::from).toList();
        return ResponseEntity.ok(ApiResponse.success("Users found", userDtos));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> update(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateUserRequest request,
                                                       Authentication authentication) {
        User actingUser = getCurrentUserUseCase.getCurrentUser(authentication.getName());
        User updated = updateUserUseCase.update(new UpdateUserUseCase.UpdateCommand(
                id,
                actingUser.getId(),
                actingUser.getRole(),
                request.getFirstName(),
                request.getLastName(),
                request.getUserName(),
                request.getEmail(),
                request.getPhone()
        ));
        return ResponseEntity.ok(ApiResponse.success("Profile updated", UserDto.from(updated)));
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllCustomers() {
        List<User> customers = getUserUseCase.findAll();
        List<UserDto> customerDtos = customers.stream()
                .filter(user -> user.getRole().name().equals("CUSTOMER"))
                .map(UserDto::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Customers found", customerDtos));
    }

    /** Deliberately self-service only — no admin override to change someone else's password. */
    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@PathVariable Long id,
                                                            @Valid @RequestBody ChangePasswordRequest request,
                                                            Authentication authentication) {
        User actingUser = getCurrentUserUseCase.getCurrentUser(authentication.getName());
        if (!actingUser.getId().equals(id)) {
            throw new AccessDeniedException("You can only modify your own password");
        }
        changePasswordUseCase.changePassword(new ChangePasswordUseCase.ChangePasswordCommand(
                id,
                request.getCurrentPassword(),
                request.getNewPassword(),
                request.getConfirmPassword()
        ));
        return ResponseEntity.ok(ApiResponse.success("Password updated"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication authentication) {
        User actingUser = getCurrentUserUseCase.getCurrentUser(authentication.getName());
        deleteUserUseCase.delete(new DeleteUserUseCase.DeleteCommand(id, actingUser.getId(), actingUser.getRole()));
        return ResponseEntity.ok(ApiResponse.success("Utilisateur supprimé"));
    }

}
