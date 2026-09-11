package ken.vivid.product.adapter.out.persistence.product;

import ken.vivid.product.application.port.out.product.DeleteProductPort;
import ken.vivid.product.application.port.out.product.LoadProductPort;
import ken.vivid.product.application.port.out.product.SaveProductPort;
import ken.vivid.product.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements LoadProductPort, SaveProductPort, DeleteProductPort {

    private final ProductJpaRepository jpaRepository;
    private final ProductMapper mapper;

    @Override
    public Optional<Product> loadById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Product> loadByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<Product> loadAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public Product save(Product product) {
        ProductJpaEntity saved = jpaRepository.save(mapper.toEntity(product));
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(Long id, Long actingUserId) {
       jpaRepository.deleteById(id);
    }
}