package ken.vivid.auth.application.service;

import ken.vivid.auth.domain.model.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleHierarchyService {

    public boolean canAssignRole(Role actingUserRole, Role targetRole) {
        return switch (actingUserRole) {
            case ADMIN -> true;
            case MANAGER -> targetRole == Role.EMPLOYEE || targetRole == Role.CUSTOMER;
            case EMPLOYEE, CUSTOMER -> false;
        };
    }

    public boolean canModifyUser(Role actingUserRole, Role targetUserRole) {
        return switch (actingUserRole) {
            case ADMIN -> true;
            case MANAGER -> targetUserRole != Role.ADMIN;
            case EMPLOYEE -> targetUserRole == Role.CUSTOMER;
            case CUSTOMER -> false;
        };
    }
}
