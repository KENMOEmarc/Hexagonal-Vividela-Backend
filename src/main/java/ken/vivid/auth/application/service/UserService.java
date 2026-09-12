package ken.vivid.auth.application.service;

import ken.vivid.auth.application.port.in.updateUser.ChangePasswordUseCase;
import ken.vivid.auth.application.port.in.deleteUser.DeleteUserUseCase;
import ken.vivid.auth.application.port.in.updateUser.UpdateUserUseCase;
import ken.vivid.auth.application.port.out.*;
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

    private final LoadUser loadUser;
    private final SaveUser saveUser;
    private final DeleteUser deleteUser;
    private final PasswordEncoder passwordEncoder;

    public UserService(LoadUser loadUser, SaveUser saveUser, DeleteUser deleteUser, PasswordEncoder passwordEncoder) {
        this.loadUser = loadUser;
        this.saveUser = saveUser;
        this.deleteUser = deleteUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User update(UpdateCommand command) {
        User target = loadUser.loadById(command.targetUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + command.targetUserId()));

        boolean isSelfUpdate = command.actingUserId().equals(command.targetUserId());

        if (!target.getEmail().equals(command.email()) && loadUser.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException("This email is already in use : " + command.email());
        }
        if (!target.getUserName().equals(command.userName()) && loadUser.existsByUserName(command.userName())) {
            throw new UserAlreadyExistsException("This username is already taken : " + command.userName());
        }

        target.setFirstName(command.firstName());
        target.setLastName(command.lastName());
        target.setUserName(command.userName());
        target.setEmail(command.email());
        target.setPhone(command.phone());

        return saveUser.save(target);
    }

    @Override
    public User changePassword(ChangePasswordCommand command) {
        User user = loadUser.loadById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + command.userId()));

        if (!passwordEncoder.matches(command.currentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }
        if (!command.newPassword().equals(command.confirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.hash(command.newPassword()));
        return saveUser.save(user);
    }

    @Override
    public void delete(DeleteCommand command) {
        User target = loadUser.loadById(command.targetUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + command.targetUserId()));

        if (command.actingUserId().equals(command.targetUserId())) {
            throw new InvalidRequestException("You cannot delete your own account");
        }

        if (target.getRole() == Role.ADMIN && loadUser.countByRole(Role.ADMIN) <= 1) {
            throw new InvalidRequestException("Cannot delete the last administrator account");
        }

        deleteUser.delete(command.targetUserId());
    }
}