package tt.hashtranslator.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tt.hashtranslator.dto.ApplicationDto;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.exception.CommonException;
import tt.hashtranslator.service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * REST controller for interacting with application.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
@Tag(name = "Application API", description = "API for interacting with application for decoding hashes")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final RestTemplate client;
    @Value("${auth-service.url}")
    private String authServiceUrl;

    /**
     * Endpoint for sending application to decode MD5 hashes.
     *
     * @param applicationDto {@link ApplicationDto}
     * @param request        {@link HttpServletRequest}
     * @return {@link ResponseEntity<String>} with http status
     */
    @Operation(
            summary = "Send application to decode MD5 hashes",
            description = "Endpoint for sending application to decode MD5 hashes",
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Application accepted message.",
                            content = @Content(mediaType = TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(
                                            value = "Application[id: 6345d54jh34ghs] has been accepted"))),
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
    public ResponseEntity<String> send(@Valid @RequestBody ApplicationDto applicationDto, HttpServletRequest request) {
        log.info("Request to decode hashes...");
        checkAuth(request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<>(applicationService.process(applicationDto), HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint for getting application by id.
     *
     * @param id      application id
     * @param request {@link HttpServletRequest}
     * @return {@link ResponseEntity<Application>} with http status
     */
    @Operation(
            summary = "Get application",
            description = "Endpoint for getting application by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found application.",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Application.class))),
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
                                    examples = @ExampleObject(value = "Application[id: 6345d54jh34ghs] not found"))),
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
    @GetMapping("{id}")
    public ResponseEntity<Application> get(@PathVariable String id, HttpServletRequest request) {
        log.info("Request to get application[id: {}]", id);
        checkAuth(request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<>(applicationService.getById(id), HttpStatus.OK);
    }

    /**
     * Sends request to auth-service for checking authentication.
     *
     * @param token basic token
     */
    private void checkAuth(String token) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        ResponseEntity<Object> response = client.exchange(authServiceUrl, HttpMethod.POST, httpEntity, Object.class);
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new CommonException("Unauthorized. Check your credentials", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
