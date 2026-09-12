package ken.vivid.product.adapter.out.persistence.product;

import ken.vivid.product.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductJpaEntity entity) {
        return Product.createProduct(
                entity.getId(),
                entity.getName(),
                entity.getThresholdValue(),
                entity.getMeasurementUnit(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ProductJpaEntity toEntity(Product product) {
        return ProductJpaEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .thresholdValue(product.getThresholdValue())
                .measurementUnit(product.getMeasurementUnit())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}