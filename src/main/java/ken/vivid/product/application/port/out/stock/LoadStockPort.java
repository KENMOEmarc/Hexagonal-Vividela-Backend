package ken.vivid.product.application.port.out.stock;

import ken.vivid.product.domain.model.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoadStockPort {
    Optional<Stock> loadById(Long id);

    List<Stock> loadAvailableByProductOrderedByExpiration(Long productId);

    BigDecimal totalQuantityByProduct(Long productId);

}
