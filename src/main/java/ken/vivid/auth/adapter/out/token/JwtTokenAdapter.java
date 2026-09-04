package ken.vivid.auth.adapter.out.token;

import com.google.api.client.util.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import ken.vivid.auth.application.port.out.TokenGeneratorPort;
import ken.vivid.auth.domain.model.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenAdapter implements TokenGeneratorPort {

    private static final int MIN_SECRET_LENGTH_BYTES = 32;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMillis;

    private SecretKey signingKey;

    @PostConstruct
    void validateAndInitSecret() {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length < MIN_SECRET_LENGTH_BYTES) {
            throw new IllegalStateException(
                    "jwt.secret doit contenir au moins " + MIN_SECRET_LENGTH_BYTES + " octets (HS256)");
        }
        this.signingKey = Keys.hmacShaKeyFor(secretBytes);
    }

    @Override
    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMillis))
                .signWith(signingKey)
                .compact();
    }

    @Override
    public Optional<String> validateAndExtractEmail(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(signingKey).build()
                    .parseSignedClaims(token).getPayload();
            return Optional.of(claims.getSubject());
        } catch (Exception e) {
            return Optional.empty(); // token invalide, expiré, signature incorrecte...
        }
    }

    @Override
    public long getExpirationMillis() {
        return expirationMillis;
    }
}
