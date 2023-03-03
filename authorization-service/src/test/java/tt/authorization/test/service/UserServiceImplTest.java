package tt.authorization.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import tt.authorization.dto.UserDto;
import tt.authorization.entity.User;
import tt.authorization.enums.Role;
import tt.authorization.repository.UserRepository;
import tt.authorization.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    public static UserDto userDto;
    public static User user;
    @BeforeAll
    public static void beforeClass() {
        userDto = new UserDto("alex@mail.com", "user12345");
        user = new User(0L ,"alex@mail.com", "user12345", Role.ROLE_USER);
    }

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void create() {
        when(userRepository.save(new User(userDto))).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("user12345");
        assertEquals(user, userService.create(userDto));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void delete() {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        userService.delete(0L);
        verify(userRepository, times(1)).delete(user);
    }
}