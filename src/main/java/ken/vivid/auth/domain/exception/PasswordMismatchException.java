package ken.vivid.auth.domain.exception;

import ken.vivid.shared.domain.exception.InvalidRequestException;

public class PasswordMismatchException extends InvalidRequestException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
