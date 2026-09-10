package ken.vivid.product.application.port.in.stock;

import ken.vivid.product.domain.model.Product;

import java.util.List;

public interface ListStockProductUseCase {
    List<Product> listStockProducts();
}
