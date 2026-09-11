package ken.vivid.product.adapter.out.persistence.product;

import ken.vivid.product.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductJpaEntity entity) {
        return new Product.Builder()
                .id(entity.getId())
                .name(entity.getName())
                .thresholdValue(entity.getThresholdValue())
                .measurementUnit(entity.getMeasurementUnit())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
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