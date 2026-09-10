package ken.vivid.product.application.port.in.product;

import ken.vivid.product.domain.model.Product;

import java.util.List;

public interface GetProductUseCase {
    Product getById(Long productId);
    List<Product> getAllProducts();
}
