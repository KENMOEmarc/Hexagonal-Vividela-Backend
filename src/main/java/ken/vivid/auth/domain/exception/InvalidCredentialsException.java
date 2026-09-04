package ken.vivid.auth.domain.exception;

import ken.vivid.shared.domain.exception.DomainException;

public class InvalidCredentialsException extends DomainException {
    protected InvalidCredentialsException(String message) {
        super(message);
    }

    protected InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
