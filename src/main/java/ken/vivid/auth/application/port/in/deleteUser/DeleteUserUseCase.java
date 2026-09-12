package ken.vivid.auth.application.port.in.deleteUser;

public interface DeleteUserUseCase {
    void delete(Long id);

    //TODO Create a command repository to list all Command
}
