package ken.vivid.product.application.port.in.stock;

import java.math.BigDecimal;

public interface ConsumeStockUseCase {
    void consume(ConsumeStockCommand command);
}
