package ken.vivid.product.adapter.out.persistence.stock.movement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementJpaRepository extends JpaRepository<StockMovementJpaEntity, Long> {
}