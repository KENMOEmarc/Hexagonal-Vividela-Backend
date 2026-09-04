package ken.vivid.auth.application.port.out;

public interface PasswordEncoderPort {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}
