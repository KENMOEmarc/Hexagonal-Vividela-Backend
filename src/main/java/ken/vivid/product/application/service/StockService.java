package ken.vivid.product.application.service;


import ken.vivid.product.application.port.in.stock.AdjustStockUseCase;
import ken.vivid.product.application.port.in.stock.ConsumeStockUseCase;
import ken.vivid.product.application.port.in.stock.RegisterStockEntryUseCase;
import ken.vivid.product.application.port.out.product.LoadProductPort;
import ken.vivid.product.application.port.out.product.SaveProductRegistrationPort;
import ken.vivid.product.application.port.out.stock.LoadStockPort;
import ken.vivid.product.application.port.out.stock.SaveStockMovementPort;
import ken.vivid.product.application.port.out.stock.SaveStockPort;
import ken.vivid.product.domain.exception.InsufficientStockException;
import ken.vivid.product.domain.model.Product;
import ken.vivid.product.domain.model.ProductRegistration;
import ken.vivid.product.domain.model.Stock;
import ken.vivid.product.domain.model.StockMovement;
import ken.vivid.product.domain.model.enums.MovementType;
import ken.vivid.shared.domain.exception.InvalidRequestException;
import ken.vivid.shared.domain.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

public class StockService implements RegisterStockEntryUseCase, ConsumeStockUseCase, AdjustStockUseCase {

    private final LoadProductPort loadProductPort;
    private final LoadStockPort loadStockPort;
    private final SaveStockPort saveStockPort;
    private final SaveProductRegistrationPort saveProductRegistrationPort;
    private final SaveStockMovementPort saveStockMovementPort;
    private final StockAllocationPolicy stockAllocationPolicy;

    public StockService(LoadProductPort loadProductPort, LoadStockPort loadStockPort, SaveStockPort saveStockPort, SaveProductRegistrationPort saveProductRegistrationPort, SaveStockMovementPort saveStockMovementPort, StockAllocationPolicy stockAllocationPolicy) {
        this.loadProductPort = loadProductPort;
        this.loadStockPort = loadStockPort;
        this.saveStockPort = saveStockPort;
        this.saveProductRegistrationPort = saveProductRegistrationPort;
        this.saveStockMovementPort = saveStockMovementPort;
        this.stockAllocationPolicy = stockAllocationPolicy;
    }

    @Override
    public Stock register(RegisterStockCommand command) {
        if (command.quantity() == null || command.quantity().signum() <= 0) {
            throw new InvalidRequestException("Quantity must be strictly positive");
        }

        Product product = loadProductPort.loadById(command.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found : " + command.productId()));

        Instant now = Instant.now();

        Stock newLot = new Stock.Builder()
                .productId(product.getId())
                .quantity(command.quantity())
                .unitPrice(command.unitPrice())
                .entryDate(now)
                .expirationDate(command.expirationDate() == null ? null : command.expirationDate().atStartOfDay(ZoneOffset.UTC).toInstant())
                .updatedAt(now)
                .build();

        Stock savedLot = saveStockPort.save(newLot);

        ProductRegistration registration = new ProductRegistration.Builder()
                .productId(product.getId())
                .quantity(command.quantity())
                .registrationType(command.registrationType())
                .notes(command.notes())
                .registeredAt(now)
                .build();
        saveProductRegistrationPort.save(registration);

        StockMovement movement = new StockMovement.Builder()
                .stockId(savedLot.getId())
                .userId(command.employeeUserId())
                .quantity(command.quantity())
                .movementType(MovementType.RESTOCK)
                .notes(command.notes())
                .movementDate(now)
                .build();
        saveStockMovementPort.save(movement);

        return savedLot;
    }

    @Override
    public void consume(ConsumeStockCommand command) {
        if (command.quantity() == null || command.quantity().signum() <= 0) {
            throw new InvalidRequestException("Quantity must be strictly positive");
        }

        loadProductPort.loadById(command.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found : " + command.productId()));

        BigDecimal totalAvailable = loadStockPort.totalQuantityByProduct(command.productId());
        if (totalAvailable == null || totalAvailable.compareTo(command.quantity()) < 0) {
            throw new InsufficientStockException(
                    "Insufficient stock for product " + command.productId() + ": requested " + command.quantity()
                            + ", available " + (totalAvailable == null ? BigDecimal.ZERO : totalAvailable));
        }

        List<Stock> availableStocks = loadStockPort.loadAvailableByProductOrderedByExpiration(command.productId());
        List<StockAllocationPolicy.Allocation> allocations = stockAllocationPolicy.allocate(availableStocks, command.quantity());

        Instant now = Instant.now();
        String notes = command.treatmentId() == null
                ? command.notes()
                : appendReference(command.notes(), "treatment #" + command.treatmentId());

        for (StockAllocationPolicy.Allocation allocation : allocations) {
            Stock lot = allocation.stock();
            lot.setQuantity(lot.getQuantity().subtract(allocation.quantityToConsume()));
            lot.setUpdatedAt(now);
            saveStockPort.save(lot);

            StockMovement movement = new StockMovement.Builder()
                    .stockId(lot.getId())
                    .userId(command.actingUserId())
                    .quantity(allocation.quantityToConsume())
                    .movementType(MovementType.CONSUMPTION)
                    .notes(notes)
                    .movementDate(now)
                    .build();
            saveStockMovementPort.save(movement);
        }
    }

    @Override
    public void adjust(AdjustStockCommand command) {
        if (command.newStockLevel() == null || command.newStockLevel().signum() < 0) {
            throw new InvalidRequestException("New stock level cannot be negative");
        }

        Stock stock = loadStockPort.loadById(command.stockId())
                .orElseThrow(() -> new ResourceNotFoundException("Stock lot not found : " + command.stockId()));

        BigDecimal previousQuantity = stock.getQuantity() == null ? BigDecimal.ZERO : stock.getQuantity();
        BigDecimal delta = command.newStockLevel().subtract(previousQuantity);

        if (delta.signum() == 0) {
            return;
        }

        Instant now = Instant.now();
        stock.setQuantity(command.newStockLevel());
        stock.setUpdatedAt(now);
        saveStockPort.save(stock);

        StockMovement movement = new StockMovement.Builder()
                .stockId(stock.getId())
                .userId(command.actingUserId())
                .quantity(delta.abs())
                .movementType(MovementType.ADJUSTMENT)
                .notes(command.notes())
                .movementDate(now)
                .build();
        saveStockMovementPort.save(movement);
    }

    private String appendReference(String notes, String reference) {
        if (notes == null || notes.isBlank()) {
            return reference;
        }
        return notes + " (" + reference + ")";
    }
}