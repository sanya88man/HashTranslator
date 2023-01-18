package tt.authorization.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tt.authorization.dto.UserDto;
import tt.authorization.service.UserService;

import javax.validation.Valid;

import static java.lang.String.format;

/**
 * REST controller for manipulating users.
 * @see tt.authorization.entity.User
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * Endpoint for creating user.
     *
     * @param userDto {@link UserDto}
     * @return {@link ResponseEntity} with http status
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> create(@Valid @RequestBody UserDto userDto) {
        log.info("Request to create user {}", userDto.getEmail());
        userService.create(userDto);
        return new ResponseEntity<>(format("User %s has been created.", userDto.getEmail()), HttpStatus.CREATED);
    }

    /**
     * Endpoint for deleting user.
     *
     * @param id user id
     * @return {@link ResponseEntity} with http status
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        log.info("Request to delete user with id: {}", id);
        userService.delete(id);
        return new ResponseEntity<>(format("User %d has been deleted.", id), HttpStatus.OK);
    }
}
