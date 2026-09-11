package ken.vivid.product.adapter.in.web;

import jakarta.validation.Valid;
import ken.vivid.product.adapter.in.web.dto.ProductDto;
import ken.vivid.product.adapter.in.web.payloads.CreateProductRequest;
import ken.vivid.product.adapter.in.web.payloads.UpdateProductRequest;
import ken.vivid.product.application.port.in.product.CreateProductUseCase;
import ken.vivid.product.application.port.in.product.DeleteProductUseCase;
import ken.vivid.product.application.port.in.product.GetProductUseCase;
import ken.vivid.product.application.port.in.product.UpdateProductUseCase;
import ken.vivid.auth.application.port.in.GetCurrentUserUseCase;
import ken.vivid.auth.domain.model.User;
import ken.vivid.shared.adapter.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAll() {
        List<ProductDto> products = getProductUseCase.getAllProducts().stream()
                .map(ProductDto::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Products", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getById(@PathVariable Long id) {
        ProductDto product = ProductDto.from(getProductUseCase.getById(id));
        return ResponseEntity.ok(ApiResponse.success("Product", product));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<ProductDto>> create(@Valid @RequestBody CreateProductRequest request) {
        ProductDto product = ProductDto.from(createProductUseCase.create(new CreateProductUseCase.CreateProductCommand(
                request.getName(),
                request.getThresholdValue(),
                request.getMeasurementUnit()
        )));
        return ResponseEntity.ok(ApiResponse.success("Product created", product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<ProductDto>> update(@PathVariable Long id,
                                                          @Valid @RequestBody UpdateProductRequest request) {
        ProductDto product = ProductDto.from(updateProductUseCase.update(new UpdateProductUseCase.UpdateProductCommand(
                id,
                null,
                request.getName(),
                request.getThresholdValue(),
                request.getMeasurementUnit()
        )));
        return ResponseEntity.ok(ApiResponse.success("Product updated", product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication authentication) {
        User actingUser = getCurrentUserUseCase.getCurrentUser(authentication.getName());
        deleteProductUseCase.delete(new DeleteProductUseCase.DeleteProductCommand(id, actingUser.getId()));
        return ResponseEntity.ok(ApiResponse.success("Product deleted"));
    }
}