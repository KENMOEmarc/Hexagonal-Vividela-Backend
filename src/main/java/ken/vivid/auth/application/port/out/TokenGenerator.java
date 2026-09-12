package ken.vivid.auth.application.port.out;

import ken.vivid.auth.domain.model.User;

import java.util.Optional;

public interface TokenGenerator {

    String generateToken(User user);

    Optional<String> validateAndExtractEmail(String token);

    long getExpirationMillis();

}
