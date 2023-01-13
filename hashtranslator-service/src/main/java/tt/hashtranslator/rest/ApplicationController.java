package tt.hashtranslator.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {
    private static final String AUTH_SERVER_URL = "http://localhost:8080/api/users/auth";

    private final ApplicationService applicationService;
    private final RestTemplate client;

    @PostMapping
    public ResponseEntity<Long> sendApplication(@RequestBody ApplicationDto applicationDto, HttpServletRequest request) {
        log.info("Request to decode hashes...");
        checkAuth(request.getHeader("Authorization"));
        return new ResponseEntity<>(applicationService.decodeHashes(applicationDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Application> getApplication(@PathVariable long id) {
        log.info("Request to get application info...");
        return new ResponseEntity<>(applicationService.getById(id), HttpStatus.OK);
    }

    private void checkAuth(String token) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        headers.set("Authorization", token);
        ResponseEntity<Object> response = client.exchange(AUTH_SERVER_URL, HttpMethod.GET, httpEntity, Object.class);
        if (response.getStatusCode().value() != 200) {
            throw new CommonException("Unauthorized. Check your credentials", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
