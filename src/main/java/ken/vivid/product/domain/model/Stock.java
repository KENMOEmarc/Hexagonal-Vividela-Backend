package ken.vivid.product.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Stock {
    private final Long id;
    private final Long productId;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Instant entryDate;
    private Instant expirationDate;
    private Instant updatedAt;

    private Stock(Long id, Long productId, BigDecimal quantity, Instant updatedAt, Instant entryDate, BigDecimal unitPrice, Instant expirationDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.entryDate = entryDate;
        this.unitPrice = unitPrice;
        this.expirationDate = expirationDate;
    }

    public static Stock createStock(Long id, Long productId, BigDecimal quantity, Instant updatedAt, Instant entryDate, BigDecimal unitPrice, Instant expirationDate) {
        return new Stock(id, productId, quantity, updatedAt, entryDate, unitPrice, expirationDate);
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Instant getEntryDate() {
        return entryDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}