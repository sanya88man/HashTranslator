package tt.hashtranslator.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.valueOf;

/**
 * Controller for handling exceptions.
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    /**
     * Handles common runtime exception.
     *
     * @param exception {@link CommonException}
     * @return {@link ResponseEntity} with error message
     */
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<String> handleCommonException(CommonException exception) {
        return new ResponseEntity<>(exception.getMessage(), valueOf(exception.getHttpCode()));
    }

    /**
     * Handles all exceptions.
     *
     * @param exception {@link Exception}
     * @return {@link ResponseEntity} with error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), valueOf(INTERNAL_SERVER_ERROR.value()));
    }
}
