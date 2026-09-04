package ken.vivid.auth.adapter.out.persistence;

import ken.vivid.auth.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getHashedPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getRole(),
                entity.isEnabled()
        );
    }

    public UserJpaEntity toEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .hashedPassword(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole())
                .enabled(user.getActive())
                .build();
    }
}