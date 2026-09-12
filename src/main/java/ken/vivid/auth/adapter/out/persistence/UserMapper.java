package ken.vivid.auth.adapter.out.persistence;

import ken.vivid.auth.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserJpaEntity entity) {
        return User.createUser(
                entity.getId(),
                entity.getRole(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getUserName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getHashedPassword()
        );
    }

    public UserJpaEntity toEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .hashedPassword(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .phone(user.getPhone())
                .role(user.getRole())
                .loyaltyPoints(user.getLoyaltyPoints() != null ? user.getLoyaltyPoints() : 0)
                .enabled(Boolean.TRUE.equals(user.getActive()))
                .build();
    }
}