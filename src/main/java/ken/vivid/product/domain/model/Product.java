package ken.vivid.product.domain.model;

import ken.vivid.product.domain.model.enums.MeasurementUnit;

import java.math.BigDecimal;
import java.time.Instant;

public class Product {

    private final Long productId;
    private Long id;
    private String name;
    private BigDecimal thresholdValue;
    private MeasurementUnit measurementUnit;
    private Instant createdAt;
    private Instant updatedAt;

    private Product(Long productId, String name, BigDecimal thresholdValue, MeasurementUnit measurementUnit, Instant createdAt, Instant updatedAt) {
        this.productId = productId;
        this.name = name;
        this.thresholdValue = thresholdValue;
        this.measurementUnit = measurementUnit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Product createProduct(Long productId, String name, BigDecimal thresholdValue, MeasurementUnit measurementUnit, Instant createdAt, Instant updatedAt) {
        return new Product(productId, name, thresholdValue, measurementUnit, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(BigDecimal thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
