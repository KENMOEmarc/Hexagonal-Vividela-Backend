package ken.vivid.product.application.port.out.product;

import ken.vivid.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface LoadProduct {
    Optional<Product> loadById(Long id);

    Optional<Product> loadByName(String name);

    List<Product> loadAll();

    boolean existsByName(String name);
}
