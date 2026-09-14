package ken.vivid.product.adapter.exception;

import ken.vivid.exception.InvalidRequestException;

public class InsufficientStockException extends InvalidRequestException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
