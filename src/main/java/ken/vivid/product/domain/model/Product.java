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

    public Product(Long productId, Long id, String name, BigDecimal thresholdValue, MeasurementUnit measurementUnit, Instant updatedAt, Instant createdAt) {
        this.productId = productId;
        this.id = id;
        this.name = name;
        this.thresholdValue = thresholdValue;
        this.measurementUnit = measurementUnit;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Product(Long productId, String name, BigDecimal thresholdValue, MeasurementUnit measurementUnit, Instant createdAt, Instant updatedAt) {
        this.productId = productId;
        this.name = name;
        this.thresholdValue = thresholdValue;
        this.measurementUnit = measurementUnit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public static class Builder {
        private Long id;
        private String name;
        private Long productId;
        private BigDecimal thresholdValue;
        private MeasurementUnit measurementUnit;
        private Instant createdAt;
        private Instant updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder productId(Long productId) { this.productId = productId; return this; }
        public Builder thresholdValue(BigDecimal thresholdValue) { this.thresholdValue = thresholdValue; return this; }
        public Builder measurementUnit(MeasurementUnit measurementUnit) { this.measurementUnit = measurementUnit; return this; }
        public Builder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public Product build() {
            return new Product(productId, id, name, thresholdValue, measurementUnit, updatedAt, createdAt);
        }
    }
}
