package ken.vivid.auth.domain.model;

import ken.vivid.auth.domain.model.enums.Role;

public class User {
    private Long id;
    private Role role;
    private String firstName;
    private String lastName;
    private String userName;
    private String phone;
    private String email;
    private String password;
    private Integer loyaltyPoints;
    private Boolean isActive;

    public User() {


    }

    public User(Long id, Role role, String firstName, String lastName, String userName, String phone, String email, String password) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User(Role role, String firstName, String lastName, String userName, String phone, String email, String password) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User(Role role, String firstName, String lastName, String userName, String phone, String email, String password, Integer loyaltyPoints, Boolean isActive) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.loyaltyPoints = loyaltyPoints;
        this.isActive = isActive;
    }

    public User(Long id, String email, String hashedPassword, String firstName, String lastName, String phone, Role role, boolean enabled) {
        this.id = id;
        this.email = email;
        this.password = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.isActive = enabled;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static class Builder {
        private Long id;
        private Role role;
        private String firstName;
        private String lastName;
        private String userName;
        private String phone;
        private String email;
        private String password;
        private Integer loyaltyPoints;
        private Boolean isActive;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder loyaltyPoints(Integer loyaltyPoints) {
            this.loyaltyPoints = loyaltyPoints;
            return this;
        }

        public Builder active(Boolean active) {
            this.isActive = active;
            return this;
        }

        public User build() {
            User user = new User();
            user.id = this.id;
            user.role = this.role;
            user.firstName = this.firstName;
            user.lastName = this.lastName;
            user.userName = this.userName;
            user.phone = this.phone;
            user.email = this.email;
            user.password = this.password;
            user.loyaltyPoints = this.loyaltyPoints;
            user.isActive = this.isActive;
            return user;
        }
    }
}
