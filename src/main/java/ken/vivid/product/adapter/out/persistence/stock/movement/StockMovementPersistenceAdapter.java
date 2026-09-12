package ken.vivid.product.adapter.out.persistence.stock.movement;

import ken.vivid.product.application.port.out.stock.SaveStockMovement;
import ken.vivid.product.domain.model.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockMovementPersistenceAdapter implements SaveStockMovement {

    private final StockMovementJpaRepository jpaRepository;
    private final StockMovementMapper mapper;

    @Override
    public StockMovement save(StockMovement movement) {
        StockMovementJpaEntity saved = jpaRepository.save(mapper.toEntity(movement));
        return mapper.toDomain(saved);
    }
}