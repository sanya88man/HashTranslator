package tt.authorization.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.valueOf;

/**
 * Controller for handling exceptions.
 */
@Slf4j
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    /**
     * Handles common runtime exception.
     *
     * @param exception {@link CommonException}
     * @return {@link ResponseEntity} with error message and http status
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<String> handleCommonException(CommonException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), valueOf(exception.getHttpCode()));
    }

    /**
     * Handles all exceptions.
     *
     * @param exception {@link Exception}
     * @return {@link ResponseEntity} with error message and http status 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), valueOf(INTERNAL_SERVER_ERROR.value()));
    }

    /**
     * Handles AccessDeniedException.
     *
     * @param exception {@link AccessDeniedException}
     * @return {@link ResponseEntity} with error message and http status 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), valueOf(FORBIDDEN.value()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return logAndCreateResponse(ex);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return logAndCreateResponse(ex);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return logAndCreateResponse(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return logAndCreateResponse(ex);
    }

    /**
     * Builds response for bad request status.
     *
     * @param ex exception to make response for
     * @return {@link ResponseEntity} with error message and http status 400
     */
    private static ResponseEntity<Object> logAndCreateResponse(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "Error during validation, please check your request parameters or request body");
    }
}
