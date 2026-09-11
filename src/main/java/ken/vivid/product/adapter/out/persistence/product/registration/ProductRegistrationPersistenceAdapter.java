package ken.vivid.product.adapter.out.persistence.product.registration;

import ken.vivid.product.application.port.out.product.SaveProductRegistrationPort;
import ken.vivid.product.domain.model.ProductRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRegistrationPersistenceAdapter implements SaveProductRegistrationPort {

    private final ProductRegistrationJpaRepository jpaRepository;
    private final ProductRegistrationMapper mapper;

    @Override
    public ProductRegistration save(ProductRegistration registration) {
        ProductRegistrationJpaEntity saved = jpaRepository.save(mapper.toEntity(registration));
        return mapper.toDomain(saved);
    }
}