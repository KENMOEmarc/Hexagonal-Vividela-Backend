package ken.vivid.product.application.port.out.product;

import ken.vivid.product.domain.model.Product;

public interface SaveProduct {
    Product save(Product product);
}
