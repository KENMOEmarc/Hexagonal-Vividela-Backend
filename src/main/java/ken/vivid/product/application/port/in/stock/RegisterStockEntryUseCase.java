package ken.vivid.product.application.port.in.stock;

import ken.vivid.product.domain.model.Stock;
import ken.vivid.product.domain.model.enums.RegistrationType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public interface RegisterStockEntryUseCase {
    Stock register(RegisterStockCommand command);
}
