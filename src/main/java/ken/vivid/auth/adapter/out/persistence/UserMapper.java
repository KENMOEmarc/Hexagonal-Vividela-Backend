package ken.vivid.auth.adapter.out.persistence;

import ken.vivid.auth.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserJpaEntity entity) {
        return new User.Builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getHashedPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .userName(entity.getUserName())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .loyaltyPoints(entity.getLoyaltyPoints())
                .active(entity.isEnabled())
                .build();
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