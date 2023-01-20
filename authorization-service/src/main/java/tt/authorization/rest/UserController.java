package tt.authorization.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * REST controller for manipulating users.
 *
 * @see tt.authorization.entity.User
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User API", description = "API for interacting with user")
public class UserController {
    private final UserService userService;

    /**
     * Endpoint for creating user.
     *
     * @param userDto {@link UserDto}
     * @return {@link ResponseEntity} with http status
     */
    @Operation(
            summary = "Create user",
            description = "Endpoint for creating user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created user message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "User: alex@mail.com has been created"))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Bad request. Please check your request params"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized status.", content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Permission denied message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Permission denied for user: alex@mail.com"))),
                    @ApiResponse(responseCode = "406", description = "Not acceptable status.", content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "An error occurred on server"))
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> create(@Valid @RequestBody UserDto userDto) {
        log.info("Request to create user: {}", userDto.getEmail());
        userService.create(userDto);
        return new ResponseEntity<>(format("User: %s has been created.", userDto.getEmail()), HttpStatus.CREATED);
    }

    /**
     * Endpoint for deleting user.
     *
     * @param id user id
     * @return {@link ResponseEntity} with http status
     */
    @Operation(
            summary = "Delete user",
            description = "Endpoint for deleting user by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted user message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "User: alex@mail.com has been deleted"))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Bad request. Please check your request params"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized status.", content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Permission denied message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Permission denied for user: alex@mail.com"))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "User[id: 344] not found"))),
                    @ApiResponse(responseCode = "406", description = "Not acceptable status.", content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "An error occurred on server"))
                    )
            }
    )
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable long id) {
        log.info("Request to delete user[id: {}]", id);
        userService.delete(id);
        return new ResponseEntity<>(format("User[id: %d] has been deleted.", id), HttpStatus.OK);
    }
}
