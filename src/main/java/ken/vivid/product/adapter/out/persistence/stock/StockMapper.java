package ken.vivid.product.adapter.out.persistence.stock;

import ken.vivid.product.domain.model.Stock;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class StockMapper {

    public Stock toDomain(StockJpaEntity entity) {
        return Stock.createStock(
                entity.getId(),
                entity.getProductId(),
                entity.getCurrentQuantity(),
                entity.getUpdatedAt(),
                toInstant(entity.getEntryDate()),
                entity.getUnitPrice(),
                toInstant(entity.getExpirationDate())
        );
    }

    public StockJpaEntity toEntity(Stock stock) {
        return StockJpaEntity.builder()
                .id(stock.getId())
                .productId(stock.getProductId())
                .currentQuantity(stock.getQuantity())
                .unitPrice(stock.getUnitPrice())
                .entryDate(toLocalDate(stock.getEntryDate()))
                .expirationDate(toLocalDate(stock.getExpirationDate()))
                .updatedAt(stock.getUpdatedAt())
                .build();
    }

    private Instant toInstant(LocalDate date) {
        return date == null ? null : date.atStartOfDay(ZoneOffset.UTC).toInstant();
    }

    private LocalDate toLocalDate(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneOffset.UTC).toLocalDate();
    }
}