package ken.vivid.auth.application.service;

import ken.vivid.auth.application.port.in.ChangePasswordUseCase;
import ken.vivid.auth.application.port.in.DeleteUserUseCase;
import ken.vivid.auth.application.port.in.UpdateUserUseCase;
import ken.vivid.auth.application.port.out.DeleteUserPort;
import ken.vivid.auth.application.port.out.LoadUserPort;
import ken.vivid.auth.application.port.out.PasswordEncoderPort;
import ken.vivid.auth.application.port.out.SaveUserPort;
import ken.vivid.auth.domain.exception.InvalidCredentialsException;
import ken.vivid.auth.domain.exception.PasswordMismatchException;
import ken.vivid.auth.domain.exception.UserAlreadyExistsException;
import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;
import ken.vivid.shared.domain.exception.InvalidRequestException;
import ken.vivid.shared.domain.exception.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;

public class UserService implements UpdateUserUseCase,
        DeleteUserUseCase, ChangePasswordUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final DeleteUserPort deleteUserPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final RoleHierarchyService roleHierarchyService;

    public UserService(LoadUserPort loadUserPort, SaveUserPort saveUserPort, DeleteUserPort deleteUserPort, PasswordEncoderPort passwordEncoderPort, RoleHierarchyService roleHierarchyService) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.deleteUserPort = deleteUserPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.roleHierarchyService = roleHierarchyService;
    }

    @Override
    public User update(UpdateCommand command) {
        User target = loadUserPort.loadById(command.targetUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + command.targetUserId()));

        boolean isSelfUpdate = command.actingUserId().equals(command.targetUserId());
        if (!isSelfUpdate && !roleHierarchyService.canModifyUser(command.actingUserRole(), target.getRole())) {
            throw new AccessDeniedException("You are not authorized to modify this user");
        }

        if (!target.getEmail().equals(command.email()) && loadUserPort.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException("This email is already in use : " + command.email());
        }
        if (!target.getUserName().equals(command.userName()) && loadUserPort.existsByUserName(command.userName())) {
            throw new UserAlreadyExistsException("This username is already taken : " + command.userName());
        }

        target.setFirstName(command.firstName());
        target.setLastName(command.lastName());
        target.setUserName(command.userName());
        target.setEmail(command.email());
        target.setPhone(command.phone());

        return saveUserPort.save(target);
    }

    @Override
    public User changePassword(ChangePasswordCommand command) {
        User user = loadUserPort.loadById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + command.userId()));

        if (!passwordEncoderPort.matches(command.currentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }
        if (!command.newPassword().equals(command.confirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        user.setPassword(passwordEncoderPort.hash(command.newPassword()));
        return saveUserPort.save(user);
    }

    @Override
    public void delete(DeleteCommand command) {
        User target = loadUserPort.loadById(command.targetUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + command.targetUserId()));

        if (command.actingUserId().equals(command.targetUserId())) {
            throw new InvalidRequestException("You cannot delete your own account");
        }
        if (!roleHierarchyService.canModifyUser(command.actingUserRole(), target.getRole())) {
            throw new AccessDeniedException("You are not authorized to delete this user");
        }
        if (target.getRole() == Role.ADMIN && loadUserPort.countByRole(Role.ADMIN) <= 1) {
            throw new InvalidRequestException("Cannot delete the last administrator account");
        }

        deleteUserPort.delete(command.targetUserId());
    }
}