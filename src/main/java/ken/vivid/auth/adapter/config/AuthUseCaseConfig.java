package ken.vivid.auth.adapter.config;

import ken.vivid.auth.application.port.out.*;
import ken.vivid.auth.application.service.AuthService;
import ken.vivid.auth.application.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCaseConfig {

    @Bean
    public AuthService authService(LoadUser loadUser,
                                   SaveUser saveUser,
                                   PasswordEncoder passwordEncoder,
                                   TokenGenerator tokenGenerator,
                                   TokenBlacklist tokenBlacklist) {
        return new AuthService(loadUser, saveUser, passwordEncoder,
                tokenGenerator, tokenBlacklist);
    }

    @Bean
    public UserService userService(LoadUser loadUser,
                                   SaveUser saveUser,
                                   DeleteUser deleteUser,
                                   PasswordEncoder passwordEncoder) {
        return new UserService(loadUser, saveUser, deleteUser, passwordEncoder);
    }
}
