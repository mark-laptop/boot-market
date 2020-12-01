package ru.ndg.market.service.role;

import ru.ndg.market.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();
    Role saveRole(Role role);
}
