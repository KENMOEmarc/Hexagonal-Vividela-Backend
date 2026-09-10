package ken.vivid.product.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Stock {
    private Long id;
    private final Long productId;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Instant entryDate;
    private Instant expirationDate;
    private Instant updatedAt;

    public Stock(Long id, Long productId, BigDecimal quantity, Instant updatedAt, Instant entryDate, BigDecimal unitPrice, Instant expirationDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.entryDate = entryDate;
        this.unitPrice = unitPrice;
        this.expirationDate = expirationDate;
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

    public static class Builder {
        private Long id;
        private Long productId;
        private BigDecimal quantity;
        private Instant updatedAt;
        private Instant entryDate;
        private BigDecimal unitPrice;
        private Instant expirationDate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder productId(Long productId) { this.productId = productId; return this; }
        public Builder quantity(BigDecimal quantity) { this.quantity = quantity; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }
        public Builder entryDate(Instant entryDate) {
            this.entryDate = entryDate;
            return this;
        }
        public Builder expirationDate(Instant expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Stock build() {
            return new Stock(id, productId, quantity, updatedAt, entryDate, unitPrice, expirationDate);
        }

    }
}