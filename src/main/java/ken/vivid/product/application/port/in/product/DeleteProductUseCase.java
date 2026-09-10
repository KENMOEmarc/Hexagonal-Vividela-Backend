package ken.vivid.product.application.port.in.product;

public interface DeleteProductUseCase {
    void delete(DeleteProductCommand deleteCommand);

    record DeleteProductCommand(
            Long targetProductId, Long actingUserId) {
    }
}
