package tt.authorization.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * REST Controller for interacting with authorization.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorization API", description = "API for interacting with authorization")
public class AuthController {

    /**
     * Endpoint for checking user's authentication.
     *
     * @return {@link ResponseEntity} with http status
     */
    @Operation(
            summary = "Check user's auth",
            description = "Endpoint for checking user's authentication",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Status ok if user is authenticated.",
                            content = @Content),
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
                    @ApiResponse(responseCode = "406", description = "Not Acceptable status.", content = @Content),
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
    public ResponseEntity<Object> auth() {
        log.info("Request to check if user authenticated...");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
