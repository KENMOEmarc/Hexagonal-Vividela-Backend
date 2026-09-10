package ken.vivid.product.application.service;

import ken.vivid.product.application.port.in.product.CreateProductUseCase;
import ken.vivid.product.application.port.in.product.DeleteProductUseCase;
import ken.vivid.product.application.port.in.product.GetProductUseCase;
import ken.vivid.product.application.port.in.product.UpdateProductUseCase;
import ken.vivid.product.application.port.in.stock.ListStockProductUseCase;
import ken.vivid.product.application.port.out.product.DeleteProductPort;
import ken.vivid.product.application.port.out.product.LoadProductPort;
import ken.vivid.product.application.port.out.product.SaveProductPort;
import ken.vivid.product.application.port.out.stock.LoadStockPort;
import ken.vivid.product.domain.model.Product;
import ken.vivid.shared.domain.exception.DuplicateResourceException;
import ken.vivid.shared.domain.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductService implements CreateProductUseCase, UpdateProductUseCase, DeleteProductUseCase, GetProductUseCase, ListStockProductUseCase {
    private final LoadProductPort loadProductPort;
    private final SaveProductPort saveProductPort;
    private final DeleteProductPort deleteProductPort;
    private final LoadStockPort loadStockPort;

    public ProductService(LoadProductPort loadProductPort, SaveProductPort saveProductPort, DeleteProductPort deleteProductPort, LoadStockPort loadStockPort) {
        this.loadProductPort = loadProductPort;
        this.saveProductPort = saveProductPort;
        this.deleteProductPort = deleteProductPort;
        this.loadStockPort = loadStockPort;
    }


    @Override
    public Product create(CreateProductCommand command) {
        if (loadProductPort.existsByName(command.name())) {
            throw new DuplicateResourceException("Product with name " + command.name() + " already exists.");
        }

        Instant now = Instant.now();
        Product product = new Product.Builder()
                .name(command.name())
                .thresholdValue(command.thresholdValue())
                .measurementUnit(command.measurementUnit())
                .createdAt(now)
                .updatedAt(now)
                .build();

        return saveProductPort.save(product);
    }

    @Override
    public void delete(DeleteProductCommand deleteCommand) {
        loadProductPort.loadById(deleteCommand.targetProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found : " + deleteCommand.targetProductId()));

        deleteProductPort.delete(deleteCommand.targetProductId(), deleteCommand.actingUserId());
    }

    @Override
    public Product getById(Long productId) {
        return loadProductPort.loadById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found : " + productId));
    }

    @Override
    public List<Product> getAllProducts() {
        return loadProductPort.loadAll();
    }

    @Override
    public Product update(UpdateProductCommand command) {
        Product target = loadProductPort.loadById(command.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found : " + command.productId()));

        if (!target.getName().equals(command.name()) && loadProductPort.existsByName(command.name())) {
            throw new DuplicateResourceException("Product with name " + command.name() + " already exists.");
        }

        target.setName(command.name());
        target.setThresholdValue(command.thresholdValue());
        target.setMeasurementUnit(command.measurementUnit());
        target.setUpdatedAt(Instant.now());

        return saveProductPort.save(target);
    }

    @Override
    public List<Product> listStockProducts() {
        return loadProductPort.loadAll().stream()
                .filter(this::isBelowThreshold)
                .toList();
    }

    private boolean isBelowThreshold(Product product) {
        BigDecimal totalQuantity = loadStockPort.totalQuantityByProduct(product.getId());
        BigDecimal available = totalQuantity == null ? BigDecimal.ZERO : totalQuantity;
        BigDecimal threshold = product.getThresholdValue() == null ? BigDecimal.ZERO : product.getThresholdValue();
        return available.compareTo(threshold) <= 0;
    }
}
