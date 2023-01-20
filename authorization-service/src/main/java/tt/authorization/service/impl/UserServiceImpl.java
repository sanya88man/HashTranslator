package tt.authorization.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${auth-service.admin.email}")
    private String adminEmail;
    @Value("${auth-service.admin.pwd}")
    private String adminPwd;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void execute() {
        log.debug("Start creating admin: {}", adminEmail);
        createAdmin();
        log.debug("Admin: {} has been created or he already exists", adminEmail);
    }

    @Override
    public User create(UserDto userDto) {
        log.debug("Start creating user with email: {}", userDto.getEmail());
        checkExistence(userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(new User(userDto));
    }

    @Override
    public void delete(long id) {
        log.debug("Start deleting user[id: {}]", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(format("User[id: %d] not found", id), NOT_FOUND.value()));
        userRepository.delete(user);
    }

    /**
     * Creates admin if he doesn't exist.
     */
    private void createAdmin() {
        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(adminPwd);
        admin.setRole(Role.ROLE_ADMIN);
        userRepository.findUserByEmail(adminEmail).orElseGet(() -> userRepository.save(admin));
    }

    private void checkExistence(String username) {
        if (userRepository.findUserByEmail(username).isPresent()) {
            log.debug("User: {} already exists", username);
            throw new CommonException(format("User: %s already exists", username), BAD_REQUEST.value());
        }
    }
}
