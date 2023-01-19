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

/**
 * REST Controller for interacting with authorization.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorization API", description = "API for interacting with authorization")
public class AuthController {

    /**
     * Endpoint for checking auth.
     *
     * @return {@link ResponseEntity} with http status
     */
    @Operation(
            summary = "Check user's auth",
            description = "Method for checking user's auth",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Return ok if user authenticated.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request.",
                            content = @Content(mediaType = "plain/text",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Bad request. Please check your request params"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Permission denied.",
                            content = @Content(mediaType = "plain/text",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(value = "Permission denied for user: alex@mail.com"))),
                    @ApiResponse(responseCode = "406", description = "Not Acceptable", content = @Content),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred on the server.",
                            content = @Content(mediaType = "plain/text",
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
