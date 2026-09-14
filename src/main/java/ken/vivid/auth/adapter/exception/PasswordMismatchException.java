package ken.vivid.auth.adapter.exception;

import ken.vivid.exception.InvalidRequestException;

public class PasswordMismatchException extends InvalidRequestException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
