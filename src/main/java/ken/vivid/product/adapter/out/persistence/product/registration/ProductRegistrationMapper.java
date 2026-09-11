package ken.vivid.product.adapter.out.persistence.product.registration;

import ken.vivid.product.domain.model.ProductRegistration;
import org.springframework.stereotype.Component;

@Component
public class ProductRegistrationMapper {

    public ProductRegistration toDomain(ProductRegistrationJpaEntity entity) {
        return new ProductRegistration.Builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .employeeUserId(entity.getEmployeeUserId())
                .quantity(entity.getQuantity())
                .registrationType(entity.getRegistrationType())
                .notes(entity.getNotes())
                .registeredAt(entity.getRegisteredAt())
                .build();
    }

    public ProductRegistrationJpaEntity toEntity(ProductRegistration registration) {
        return ProductRegistrationJpaEntity.builder()
                .id(registration.getId())
                .productId(registration.getProductId())
                .employeeUserId(registration.getEmployeeUserId())
                .quantity(registration.getQuantity())
                .registrationType(registration.getRegistrationType())
                .notes(registration.getNotes())
                .registeredAt(registration.getRegisteredAt())
                .build();
    }
}