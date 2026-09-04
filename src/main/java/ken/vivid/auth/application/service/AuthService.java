package ken.vivid.auth.application.service;

import io.modelcontextprotocol.spec.McpSchema;
import ken.vivid.auth.application.port.in.GetCurrentUserUseCase;
import ken.vivid.auth.application.port.in.LoginUseCase;
import ken.vivid.auth.application.port.in.LogoutUseCase;
import ken.vivid.auth.application.port.in.RegisterUseCase;
import ken.vivid.auth.application.port.out.*;
import ken.vivid.auth.domain.exception.InvalidCredentialsException;
import ken.vivid.auth.domain.exception.UserAlreadyExistsException;
import ken.vivid.auth.domain.model.AuthResult;
import ken.vivid.auth.domain.model.User;
import ken.vivid.auth.domain.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.imap.protocol.UIDSet;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements LoginUseCase, LogoutUseCase, RegisterUseCase, GetCurrentUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenGeneratorPort tokenGeneratorPort;
    private final TokenBlacklistPort tokenBlacklistPort;

    @Override
    public User getCurrentUser(String email) {
        return loadUserPort.loadByEmail(email).orElseThrow(() -> new InvalidCredentialsException("User not found"));
    }

    @Override
    public AuthResult login(LoginCommand loginCommand) {

        User user = loadUserPort.loadByEmail(loginCommand.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if(!passwordEncoderPort.matches(loginCommand.rawPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if(!user.getActive()) {
            throw new InvalidCredentialsException("User is not active");
        }

        String token = tokenGeneratorPort.generateToken(user);
        return new AuthResult(token, tokenGeneratorPort.getExpirationMillis(), user.getId(), user.getEmail(), user.getRole());
    }

    @Override
    public void logout(String token) {
        tokenBlacklistPort.revoke(token);
    }

    @Override
    public User register(RegisterCommand registerCommand) {

        if (!registerCommand.rawPassword().equals(registerCommand.confirmPassword())) {
            throw new InvalidCredentialsException("Passwords do not match");
        }

        if (loadUserPort.existsByEmail(registerCommand.email())) {
            throw new UserAlreadyExistsException("Un compte existe déjà avec cet email : " + registerCommand.email());
        }

        User newUser = new User(
                Role.CUSTOMER,
                registerCommand.firstName(),
                registerCommand.lastName(),
                registerCommand.userName(),
                registerCommand.email(),
                passwordEncoderPort.hash(registerCommand.rawPassword()),
                0,
                true
        );

        return saveUserPort.save(newUser);
    }

    @Override
    public User store(StoreCommand storeCommand) {

        if (!storeCommand.rawPassword().equals(storeCommand.confirmPassword())) {
            throw new InvalidCredentialsException("Passwords do not match");
        }

        if (loadUserPort.existsByEmail(storeCommand.email())) {
            throw new UserAlreadyExistsException("Un compte existe déjà avec cet email : " + storeCommand.email());
        }

        User newUser = new User(
                Role.valueOf(storeCommand.role()),
                storeCommand.firstName(),
                storeCommand.lastName(),
                storeCommand.userName(),
                storeCommand.email(),
                passwordEncoderPort.hash(storeCommand.rawPassword()),
                0,
                true
        );

        return saveUserPort.save(newUser);
    }
}
