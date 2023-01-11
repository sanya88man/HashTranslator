package tt.authorization.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tt.authorization.dto.UserDto;
import tt.authorization.entity.User;
import tt.authorization.exception.CommonException;
import tt.authorization.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
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
}
