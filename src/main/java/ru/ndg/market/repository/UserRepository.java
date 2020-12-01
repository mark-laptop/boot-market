package ru.ndg.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ndg.market.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByFirstName(String firstName);
    User findByUsername(String username);
}
