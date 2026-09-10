package ken.vivid.product.domain.exception;

import ken.vivid.shared.domain.exception.InvalidRequestException;

public class InsufficientStockException extends InvalidRequestException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
