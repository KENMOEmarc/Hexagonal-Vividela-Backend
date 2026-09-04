package ken.vivid.auth.domain.exception;

import ken.vivid.shared.domain.exception.DomainException;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
