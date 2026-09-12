package ken.vivid.product.application.port.in.product;

import ken.vivid.product.domain.model.Product;
import ken.vivid.product.domain.model.enums.MeasurementUnit;

import java.math.BigDecimal;

public interface CreateProductUseCase {
    Product create(CreateProductCommand command);
}
