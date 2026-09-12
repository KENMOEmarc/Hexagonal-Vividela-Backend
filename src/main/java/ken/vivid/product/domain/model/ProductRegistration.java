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

    private ProductRegistration(Long id, Long productId, Long employeeUserId, BigDecimal quantity, RegistrationType registrationType, String notes, Instant registeredAt) {
        this.id = id;
        this.productId = productId;
        this.employeeUserId = employeeUserId;
        this.quantity = quantity;
        this.registrationType = registrationType;
        this.notes = notes;
        this.registeredAt = registeredAt;
    }

    public static ProductRegistration createProductRegistration(Long id, Long productId, Long employeeUserId, BigDecimal quantity, RegistrationType registrationType, String notes, Instant registeredAt) {
        return new ProductRegistration(id, productId, employeeUserId, quantity, registrationType, notes, registeredAt);
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
