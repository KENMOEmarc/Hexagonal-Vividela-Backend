package ken.vivid.product.application.port.out.product;

public interface DeleteProductPort {
    void delete(Long id, Long actingUserId);
}
