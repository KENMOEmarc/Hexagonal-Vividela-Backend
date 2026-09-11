package ken.vivid.product.adapter.out.persistence.stock;

import ken.vivid.product.domain.model.Stock;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Component
public class StockMapper {

    public Stock toDomain(StockJpaEntity entity) {
        return new Stock.Builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .quantity(entity.getCurrentQuantity())
                .unitPrice(entity.getUnitPrice())
                .entryDate(toInstant(entity.getEntryDate()))
                .expirationDate(toInstant(entity.getExpirationDate()))
                .updatedAt(entity.getUpdatedAt())
                .build();
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