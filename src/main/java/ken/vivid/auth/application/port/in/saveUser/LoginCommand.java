package ken.vivid.auth.application.port.in.saveUser;

public record LoginCommand(String email, String rawPassword) {

}