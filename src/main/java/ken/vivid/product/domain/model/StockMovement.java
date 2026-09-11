package ken.vivid.product.domain.model;

import ken.vivid.product.domain.model.enums.MovementType;

import java.math.BigDecimal;
import java.time.Instant;

public class StockMovement {
    private Long id;
    private final Long stockId;
    private final Long userId;
    private BigDecimal quantity;
    private MovementType movementType;
    private String notes;
    private Instant movementDate;

    public StockMovement(Long id, Long stockId, Long userId, BigDecimal quantity, MovementType movementType, String notes, Instant movementDate) {
        this.id = id;
        this.stockId = stockId;
        this.userId = userId;
        this.quantity = quantity;
        this.movementType = movementType;
        this.notes = notes;
        this.movementDate = movementDate;
    }

    public static class Builder {
        private Long id;
        private Long stockId;
        private Long userId;
        private BigDecimal quantity;
        private MovementType movementType;
        private String notes;
        private Instant movementDate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder stockId(Long stockId) { this.stockId = stockId; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder quantity(BigDecimal quantity) { this.quantity = quantity; return this; }
        public Builder movementType(MovementType movementType) { this.movementType = movementType; return this; }
        public Builder notes(String notes) { this.notes = notes; return this; }
        public Builder movementDate(Instant movementDate) { this.movementDate = movementDate; return this; }

        public StockMovement build() {
            return new StockMovement(id, stockId, userId, quantity, movementType, notes, movementDate);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getStockId() {
        return stockId;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public String getNotes() {
        return notes;
    }

    public Instant getMovementDate() {
        return movementDate;
    }
}
