package ken.vivid.auth.application.service;

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

public class AuthService implements LoginUseCase, LogoutUseCase, RegisterUseCase, GetCurrentUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenGeneratorPort tokenGeneratorPort;
    private final TokenBlacklistPort tokenBlacklistPort;

    public AuthService(LoadUserPort loadUserPort, SaveUserPort saveUserPort, PasswordEncoderPort passwordEncoderPort, TokenGeneratorPort tokenGeneratorPort, TokenBlacklistPort tokenBlacklistPort) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenGeneratorPort = tokenGeneratorPort;
        this.tokenBlacklistPort = tokenBlacklistPort;
    }

    @Override
    public User getCurrentUser(String email) {
        return loadUserPort.loadByEmailOrUserName(email).orElseThrow(() -> new InvalidCredentialsException("User not found"));
    }

    @Override
    public AuthResult login(LoginCommand loginCommand) {

        User user = loadUserPort.loadByEmailOrUserName(loginCommand.email())
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
    public AuthResult register(RegisterCommand registerCommand) {

        if (!registerCommand.rawPassword().equals(registerCommand.confirmPassword())) {
            throw new InvalidCredentialsException("Passwords do not match");
        }

        if (loadUserPort.existsByEmail(registerCommand.email())) {
            throw new UserAlreadyExistsException("An account already exists with this email : " + registerCommand.email());
        }

        User newUser = new User(
                Role.CUSTOMER,
                registerCommand.firstName(),
                registerCommand.lastName(),
                registerCommand.userName(),
                registerCommand.phone(),
                registerCommand.email(),
                passwordEncoderPort.hash(registerCommand.confirmPassword()),
                true
        );

        User savedUser = saveUserPort.save(newUser);
        String token = tokenGeneratorPort.generateToken(savedUser);
        return new AuthResult(token, tokenGeneratorPort.getExpirationMillis(),
                savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }

    @Override
    public User store(StoreCommand storeCommand) {

        if (loadUserPort.existsByEmail(storeCommand.email())) {
            throw new UserAlreadyExistsException("An account already exits with this emain : " + storeCommand.email());
        }

        User newUser = new User(
                storeCommand.role(),
                storeCommand.firstName(),
                storeCommand.lastName(),
                storeCommand.userName(),
                storeCommand.phone(),
                storeCommand.email(),
                passwordEncoderPort.hash(storeCommand.rawPassword())
        );

        return saveUserPort.save(newUser);
    }
}
