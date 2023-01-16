package tt.authorization.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dto for user.
 */
@Data
public class UserDto {
    @Email
    @NotBlank
    private String email;

    @Size(min = 4, max = 256, message = "Size must be between 4-256")
    @NotBlank
    private String password;
}
