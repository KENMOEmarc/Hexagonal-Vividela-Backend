package ken.vivid.product.application.port.out.stock;

import ken.vivid.product.domain.model.StockMovement;

public interface SaveStockMovement {
    StockMovement save(StockMovement movement);
}
