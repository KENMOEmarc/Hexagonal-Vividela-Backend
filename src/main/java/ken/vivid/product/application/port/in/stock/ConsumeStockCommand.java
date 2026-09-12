package ken.vivid.product.application.port.in.stock;

import java.math.BigDecimal;

public record ConsumeStockCommand(
        Long productId,
        Long actingUserId,
        Long treatmentId,
        BigDecimal quantity,
        String notes
) {
}
