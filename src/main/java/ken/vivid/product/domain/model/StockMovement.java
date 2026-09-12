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

    private StockMovement(Long id, Long stockId, Long userId, BigDecimal quantity, MovementType movementType, String notes, Instant movementDate) {
        this.id = id;
        this.stockId = stockId;
        this.userId = userId;
        this.quantity = quantity;
        this.movementType = movementType;
        this.notes = notes;
        this.movementDate = movementDate;
    }

    public static StockMovement createStockMovement(Long id, Long stockId, Long userId, BigDecimal quantity, MovementType movementType, String notes, Instant movementDate) {
        return new StockMovement(id, stockId, userId, quantity, movementType, notes, movementDate);
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
