package ken.vivid.auth.application.port.out;

public interface TokenBlacklist {

    void revoke(String token);

    boolean isRevoked(String token);
}

