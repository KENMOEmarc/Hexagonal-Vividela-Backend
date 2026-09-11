package ken.vivid.product.adapter.out.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {

    Optional<ProductJpaEntity> findByName(String name);

    boolean existsByName(String name);
}