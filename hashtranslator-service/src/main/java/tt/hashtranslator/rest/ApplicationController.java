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
    private static final String AUTH_SERVER_URL = "http://authorization-service:8080/api/auth";

    private final ApplicationService applicationService;
    private final RestTemplate client;

    @PostMapping
    public ResponseEntity<String> sendApplication(
            @RequestBody ApplicationDto applicationDto, HttpServletRequest request) {
        log.info("Request to decode hashes...");
        checkAuth(request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity<>(applicationService.process(applicationDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Application> getApplication(@PathVariable String id) {
        log.info("Request to get application with id: {}", id);
        return new ResponseEntity<>(applicationService.getById(id), HttpStatus.OK);
    }

    private void checkAuth(String token) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        ResponseEntity<Object> response = client.exchange(AUTH_SERVER_URL, HttpMethod.POST, httpEntity, Object.class);
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new CommonException("Unauthorized. Check your credentials", HttpStatus.UNAUTHORIZED.value());
        }
    }
}
