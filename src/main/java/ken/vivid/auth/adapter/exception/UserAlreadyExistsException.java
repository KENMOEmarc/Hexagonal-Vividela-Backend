package ken.vivid.auth.adapter.exception;

import ken.vivid.exception.DuplicateResourceException;

public class UserAlreadyExistsException extends DuplicateResourceException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
