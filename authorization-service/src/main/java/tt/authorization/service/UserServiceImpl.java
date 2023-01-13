package tt.authorization.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tt.authorization.dto.UserDto;
import tt.authorization.entity.User;
import tt.authorization.enums.Role;
import tt.authorization.exception.CommonException;
import tt.authorization.repository.UserRepository;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String ADMIN_EMAIL = "sanya88man@gmail.com";
    private static final String ADMIN_PWD = "$2a$10$c5COdQ7.MXjUVSiylnldl.qW7YHK2EcXUU1JVlm7QM2F5GOKELbpG";
    private final UserRepository userRepository;

    @PostConstruct
    private void execute() {
        log.info("Start creating admin with username: {}", ADMIN_EMAIL);
        createAdmin();
    }

    @Override
    public User create(UserDto userDto) {
       return userRepository.save(new User(userDto));
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException(
                        String.format("User with id %d not found", id), HttpStatus.NOT_FOUND.value()));
        userRepository.delete(user);
    }

    private void createAdmin() {
        User admin = new User();
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(ADMIN_PWD);
        admin.setRole(Role.ROLE_ADMIN);
        userRepository.findUserByEmail(ADMIN_EMAIL).orElseGet(() -> userRepository.save(admin));
    }
}
