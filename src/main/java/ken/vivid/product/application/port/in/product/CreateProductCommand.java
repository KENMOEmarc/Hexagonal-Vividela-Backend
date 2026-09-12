package ken.vivid.product.application.port.in.product;

import ken.vivid.product.domain.model.enums.MeasurementUnit;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        BigDecimal thresholdValue,
        MeasurementUnit measurementUnit
) {

}