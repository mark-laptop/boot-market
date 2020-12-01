package ru.ndg.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ndg.market.model.Role;
import sun.rmi.runtime.Log;

@Repository
public interface RoleRepository extends JpaRepository<Role, Log> {

    Role findByName(String name);
}
