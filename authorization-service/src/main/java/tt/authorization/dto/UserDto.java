package tt.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Dto for user.
 *
 * @see tt.authorization.entity.User
 */
@Data
public class UserDto {
    @Email
    @NotBlank
    @Schema(description = "User's email", example = "alex@mail.com")
    private String email;

    @NotBlank
    @Size(min = 4, max = 256, message = "Size must be between 4-256")
    @Schema(description = "User's password", example = "user12345")
    private String password;
}
