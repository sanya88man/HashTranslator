package tt.authorization.service;

import tt.authorization.dto.UserDto;
import tt.authorization.entity.User;

/**
 * Service for interacting with {@link User} entity.
 */
public interface UserService {
    /**
     * Creates {@link User} if he doesn't exist.
     *
     * @param userDto {@link UserDto}
     * @return created {@link User}
     */
    User create(UserDto userDto);

    /**
     * Deletes {@link User} by id.
     *
     * @param id user id
     */
    void delete(long id);
}
