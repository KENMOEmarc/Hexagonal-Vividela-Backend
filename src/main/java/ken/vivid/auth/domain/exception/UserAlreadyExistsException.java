package ken.vivid.auth.domain.exception;

import ken.vivid.shared.domain.exception.DuplicateResourceException;

public class UserAlreadyExistsException extends DuplicateResourceException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
