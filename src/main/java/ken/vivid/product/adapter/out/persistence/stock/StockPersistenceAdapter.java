package ken.vivid.product.adapter.out.persistence.stock;

import ken.vivid.product.application.port.out.stock.LoadStock;
import ken.vivid.product.application.port.out.stock.SaveStock;
import ken.vivid.product.domain.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StockPersistenceAdapter implements LoadStock, SaveStock {

    private final StockJpaRepository jpaRepository;
    private final StockMapper mapper;

    @Override
    public Optional<Stock> loadById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Stock> loadAvailableByProductOrderedByExpiration(Long productId) {
        return jpaRepository
                .findByProductIdAndCurrentQuantityGreaterThanOrderByExpirationDateAsc(productId, BigDecimal.ZERO)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public BigDecimal totalQuantityByProduct(Long productId) {
        return jpaRepository.sumCurrentQuantityByProductId(productId);
    }

    @Override
    public Stock save(Stock stock) {
        StockJpaEntity saved = jpaRepository.save(mapper.toEntity(stock));
        return mapper.toDomain(saved);
    }
}