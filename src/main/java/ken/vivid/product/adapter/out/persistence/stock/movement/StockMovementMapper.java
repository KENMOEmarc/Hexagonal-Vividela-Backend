package ken.vivid.product.adapter.out.persistence.stock.movement;

import ken.vivid.product.domain.model.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovement toDomain(StockMovementJpaEntity entity) {
        return StockMovement.createStockMovement(
                entity.getId(),
                entity.getStockId(),
                entity.getUserId(),
                entity.getQuantity(),
                entity.getMovementType(),
                entity.getNotes(),
                entity.getMovementDate()
        );
    }

    public StockMovementJpaEntity toEntity(StockMovement movement) {
        return StockMovementJpaEntity.builder()
                .id(movement.getId())
                .stockId(movement.getStockId())
                .userId(movement.getUserId())
                .quantity(movement.getQuantity())
                .movementType(movement.getMovementType())
                .notes(movement.getNotes())
                .movementDate(movement.getMovementDate())
                .build();
    }
}