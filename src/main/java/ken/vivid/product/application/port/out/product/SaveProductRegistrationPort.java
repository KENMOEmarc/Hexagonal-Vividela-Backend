package ken.vivid.product.application.port.out.product;

import ken.vivid.product.domain.model.ProductRegistration;

public interface SaveProductRegistrationPort {
    ProductRegistration save(ProductRegistration registration);
}
