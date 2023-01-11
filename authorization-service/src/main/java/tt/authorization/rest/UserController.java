package tt.authorization.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tt.authorization.dto.UserDto;
import tt.authorization.service.UserServiceImpl;

import static java.lang.String.format;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> create(@RequestBody UserDto userDto) {
        log.info("Request to create user {}", userDto.getEmail());
        userService.create(userDto);
        return new ResponseEntity<>(format("User %s has been created.", userDto.getEmail()), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        log.info("Request to delete user with id: {}", id);
        userService.delete(id);
        return new ResponseEntity<>(format("User %d has been deleted.", id), HttpStatus.OK);
    }

    @GetMapping("auth")
    public ResponseEntity<Object> auth() {
        log.info("Request to check if user authenticated...");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
