package ken.vivid.auth.application.port.in.updateUser;

public record ChangePasswordCommand(
        Long userId,
        String currentPassword,
        String newPassword,
        String confirmPassword
) {
}