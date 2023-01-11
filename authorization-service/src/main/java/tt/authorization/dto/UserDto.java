package tt.authorization.dto;

import lombok.Data;

/**
 * Dto for user.
 */
@Data
public class UserDto {
    private String email;
    private String password;
}
