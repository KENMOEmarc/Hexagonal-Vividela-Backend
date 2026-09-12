package ken.vivid.product.application.port.out.stock;

import ken.vivid.product.domain.model.Stock;

public interface SaveStock {
    Stock save(Stock stock);
}
