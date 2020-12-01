package ru.ndg.market.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.market.model.User;
import ru.ndg.market.repository.UserRepository;
import ru.ndg.market.service.role.RoleService;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository
                        , RoleService roleService
                        , BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.findByName("ROLE_USER"));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) throw new UsernameNotFoundException("Invalid username or password");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getUserGrandAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getUserGrandAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
