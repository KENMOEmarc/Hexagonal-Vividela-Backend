package ken.vivid.product.application.port.out.stock;

import ken.vivid.product.domain.model.StockMovement;

public interface SaveStockMovementPort {
    StockMovement save(StockMovement movement);
}
