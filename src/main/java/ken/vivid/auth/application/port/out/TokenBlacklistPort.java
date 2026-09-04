package ken.vivid.auth.application.port.out;

public interface TokenBlacklistPort {

    void revoke(String token);

    boolean isRevoked(String token);
}
