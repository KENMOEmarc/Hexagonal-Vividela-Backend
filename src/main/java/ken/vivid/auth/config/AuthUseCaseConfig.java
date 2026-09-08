package ken.vivid.auth.config;

import ken.vivid.auth.application.port.out.*;
import ken.vivid.auth.application.service.AuthService;
import ken.vivid.auth.application.service.RoleHierarchyService;
import ken.vivid.auth.application.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCaseConfig {
    @Bean
    public RoleHierarchyService roleHierarchyService() {
        return new RoleHierarchyService();
    }

    @Bean
    public AuthService authService(LoadUserPort loadUserPort,
                                   SaveUserPort saveUserPort,
                                   PasswordEncoderPort passwordEncoderPort,
                                   TokenGeneratorPort tokenGeneratorPort,
                                   TokenBlacklistPort tokenBlacklistPort) {
        return new AuthService(loadUserPort, saveUserPort, passwordEncoderPort,
                tokenGeneratorPort, tokenBlacklistPort);
    }

    @Bean
    public UserService userService(LoadUserPort loadUserPort,
                                   SaveUserPort saveUserPort,
                                   DeleteUserPort deleteUserPort,
                                   PasswordEncoderPort passwordEncoderPort,
                                   RoleHierarchyService roleHierarchyService) {
        return new UserService(loadUserPort, saveUserPort, deleteUserPort,
                passwordEncoderPort, roleHierarchyService);
    }
}
