package tt.authorization.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Endpoint for checking auth.
     *
     * @return {@link ResponseEntity}
     */
    @GetMapping
    public ResponseEntity<Object> auth() {
        log.info("Request to check if user authenticated...");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
