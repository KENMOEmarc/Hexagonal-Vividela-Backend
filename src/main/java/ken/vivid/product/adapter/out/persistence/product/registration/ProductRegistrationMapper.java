package ken.vivid.product.adapter.out.persistence.product.registration;

import ken.vivid.product.domain.model.ProductRegistration;
import org.springframework.stereotype.Component;

@Component
public class ProductRegistrationMapper {

    public ProductRegistration toDomain(ProductRegistrationJpaEntity entity) {
        return ProductRegistration.createProductRegistration(
                entity.getId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getRegistrationType(),
                entity.getNotes(),
                entity.getRegisteredAt()
        );
    }

    public ProductRegistrationJpaEntity toEntity(ProductRegistration registration) {
        return ProductRegistrationJpaEntity.builder()
                .id(registration.getId())
                .productId(registration.getProductId())
                .quantity(registration.getQuantity())
                .registrationType(registration.getRegistrationType())
                .notes(registration.getNotes())
                .registeredAt(registration.getRegisteredAt())
                .build();
    }
}