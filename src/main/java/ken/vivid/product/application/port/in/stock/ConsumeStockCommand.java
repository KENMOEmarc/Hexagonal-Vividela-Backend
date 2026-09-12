package ken.vivid.product.application.port.in.stock;

import java.math.BigDecimal;

public record ConsumeStockCommand(
        Long productId,
        Long actingUserId,
        BigDecimal quantity,
        String notes
) {
}
