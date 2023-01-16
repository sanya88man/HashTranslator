package tt.authorization.service;

import org.springframework.stereotype.Service;
import tt.authorization.dto.UserDto;
import tt.authorization.entity.User;

@Service
public interface UserService {
    User create(UserDto userDto);

    void delete(long id);
}
