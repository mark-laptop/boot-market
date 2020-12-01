package ru.ndg.market.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ndg.market.model.User;

public interface UserService extends UserDetailsService {

    User loadByFirstName(String firstName);
    User loadByUsername(String username);
    User saveUser(User user);
}
