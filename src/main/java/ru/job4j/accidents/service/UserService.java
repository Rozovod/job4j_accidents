package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthorityService authorityService;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public Optional<User> save(User user) {
        Optional<User> userOptional = Optional.empty();
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        try {
            userRepository.save(user);
            userOptional = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Пользователь с таким логином уже существует", e);
        }
        return userOptional;
    }
}
