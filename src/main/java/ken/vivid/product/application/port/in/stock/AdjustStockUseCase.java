package ken.vivid.product.application.port.in.stock;

import java.math.BigDecimal;

public interface AdjustStockUseCase {
    void adjust(AdjustStockCommand command);

    record AdjustStockCommand(
            Long stockId, Long actingUserId,
            BigDecimal newStockLevel, String notes) {
    }
}
