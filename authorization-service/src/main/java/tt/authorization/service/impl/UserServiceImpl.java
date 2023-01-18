package tt.authorization.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tt.authorization.dto.UserDto;
import tt.authorization.entity.User;
import tt.authorization.enums.Role;
import tt.authorization.exception.CommonException;
import tt.authorization.repository.UserRepository;
import tt.authorization.service.UserService;

import javax.annotation.PostConstruct;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Implementation of {@link UserService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String ADMIN_EMAIL = "sanya88man@gmail.com";
    private static final String ADMIN_PWD = "$2a$10$c5COdQ7.MXjUVSiylnldl.qW7YHK2EcXUU1JVlm7QM2F5GOKELbpG";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates admin diring  if he doesn't exist.
     */
    @PostConstruct
    private void execute() {
        log.debug("Start creating admin with username: {}", ADMIN_EMAIL);
        createAdmin();
    }

    @Override
    public User create(UserDto userDto) {
        log.debug("Start creating user with username: {}", userDto.getEmail());
        checkExistence(userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(new User(userDto));
    }

    @Override
    public void delete(long id) {
        log.debug("Start deleting user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(format("User with id %d not found", id), NOT_FOUND.value()));
        userRepository.delete(user);
    }

    private void createAdmin() {
        User admin = new User();
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(ADMIN_PWD);
        admin.setRole(Role.ROLE_ADMIN);
        userRepository.findUserByEmail(ADMIN_EMAIL).orElseGet(() -> userRepository.save(admin));
    }

    private void checkExistence(String username) {
        if (userRepository.findUserByEmail(username).isPresent()) {
            throw new CommonException(format("User: %s already exists", username), BAD_REQUEST.value());
        }
    }
}
