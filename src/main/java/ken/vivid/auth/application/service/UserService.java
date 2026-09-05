package ken.vivid.auth.application.service;

import ken.vivid.auth.application.port.in.ChangePasswordUseCase;
import ken.vivid.auth.application.port.in.DeleteUserUseCase;
import ken.vivid.auth.application.port.in.UpdateUserUseCase;
import ken.vivid.auth.application.port.out.DeleteUserPort;
import ken.vivid.auth.application.port.out.LoadUserPort;
import ken.vivid.auth.application.port.out.SaveUserPort;
import ken.vivid.auth.domain.exception.UserAlreadyExistsException;
import ken.vivid.auth.domain.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UpdateUserUseCase, DeleteUserUseCase, ChangePasswordUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final DeleteUserPort deleteUserPort;

    public UserService(LoadUserPort loadUserPort, SaveUserPort saveUserPort, DeleteUserPort deleteUserPort) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.deleteUserPort = deleteUserPort;
    }

    @Override
    public User update(UpdateCommand command) {

        User user = loadUserPort.loadById(command.userId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found: " + command.userId()
                        )
                );

        if (!user.getEmail().equals(command.email())
                && loadUserPort.loadByEmail(command.email()).isPresent()) {

            throw new UserAlreadyExistsException(
                    "Email already exists: " + command.email()
            );
        }

        user.setFirstName(command.firstName());
        user.setLastName(command.lastName());
        user.setUserName(command.userName());
        user.setEmail(command.email());
        user.setPhone(command.phone());

        return saveUserPort.save(user);
    }

    @Override
    public User changePassword(ChangePasswordCommand command) {
        User user = loadUserPort.loadById(command.userId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: {} " + command.userId()));

        if (!(user == null)) {
            user.setPassword(command.newPassword());
            saveUserPort.save(user);
        }

        return user;
    }

    @Override
    public void delete(Long id) {
        User user = loadUserPort.loadById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: {} " + id));

        if (!(user == null)) {
            deleteUserPort.delete(id);
        }
    }
}