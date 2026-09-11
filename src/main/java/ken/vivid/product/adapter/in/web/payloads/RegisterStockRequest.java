package ken.vivid.product.adapter.in.web.payloads;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import ken.vivid.product.domain.model.enums.RegistrationType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RegisterStockRequest {

    @NotNull(message = "The product id is required")
    private Long productId;

    @NotNull(message = "The quantity is required")
    @DecimalMin(value = "0.01", message = "The quantity must be strictly positive")
    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private LocalDate expirationDate;

    @NotNull(message = "The registration type is required")
    private RegistrationType registrationType;

    private String notes;
}