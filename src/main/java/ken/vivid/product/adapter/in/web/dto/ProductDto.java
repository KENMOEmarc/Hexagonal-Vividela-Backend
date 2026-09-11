package ken.vivid.product.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ken.vivid.product.domain.model.Product;
import ken.vivid.product.domain.model.enums.MeasurementUnit;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDto(
        Long id,
        String name,
        BigDecimal thresholdValue,
        MeasurementUnit measurementUnit,
        Instant createdAt,
        Instant updatedAt
) {
    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .thresholdValue(product.getThresholdValue())
                .measurementUnit(product.getMeasurementUnit())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
