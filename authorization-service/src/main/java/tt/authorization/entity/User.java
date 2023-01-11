package tt.authorization.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import tt.authorization.dto.UserDto;
import tt.authorization.enums.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private Role role;

    public User(UserDto userDto) {
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.role = Role.ROLE_USER;
    }
}
