package tt.hashtranslator.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import tt.hashtranslator.exception.CommonException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sends request to auth-service for checking authentication.
 */
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final RestTemplate client;
    @Value("${auth-service.url}")
    private String authServiceUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        headers.set(HttpHeaders.AUTHORIZATION, token);
        ResponseEntity<Object> authResponse = client.exchange(authServiceUrl, HttpMethod.POST, httpEntity, Object.class);
        if (authResponse.getStatusCode().value() != HttpStatus.OK.value()) {
            throw new CommonException("Unauthorized. Check your credentials", HttpStatus.UNAUTHORIZED.value());
        }
        return true;
    }
}
