package ken.vivid.product.domain.model;

import ken.vivid.product.domain.model.enums.RegistrationType;

import java.math.BigDecimal;
import java.time.Instant;

public class ProductRegistration {
    private final Long productId;
    private Long id;
    private Long employeeUserId;
    private BigDecimal quantity;
    private RegistrationType registrationType;
    private String notes;
    private Instant registeredAt;

    public ProductRegistration(Long id, Long productId, Long employeeUserId, BigDecimal quantity, RegistrationType registrationType, String notes, Instant registeredAt) {
        this.id = id;
        this.productId = productId;
        this.employeeUserId = employeeUserId;
        this.quantity = quantity;
        this.registrationType = registrationType;
        this.notes = notes;
        this.registeredAt = registeredAt;
    }

    public static class Builder {
        private Long id;
        private Long productId;
        private Long employeeUserId;
        private BigDecimal quantity;
        private RegistrationType registrationType;
        private String notes;
        private Instant registeredAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder productId(Long productId) { this.productId = productId; return this; }
        public Builder employeeUserId(Long employeeUserId) { this.employeeUserId = employeeUserId; return this; }
        public Builder quantity(BigDecimal quantity) { this.quantity = quantity; return this; }
        public Builder registrationType(RegistrationType registrationType) { this.registrationType = registrationType; return this; }
        public Builder notes(String notes) { this.notes = notes; return this; }
        public Builder registeredAt(Instant registeredAt) { this.registeredAt = registeredAt; return this; }

        public ProductRegistration build() {
            return new ProductRegistration(id, productId, employeeUserId, quantity, registrationType, notes, registeredAt);
        }
    }

    public Long getProductId() {
        return productId;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeUserId() {
        return employeeUserId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public String getNotes() {
        return notes;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }
}
