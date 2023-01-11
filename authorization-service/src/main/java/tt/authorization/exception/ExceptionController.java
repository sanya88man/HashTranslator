package tt.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tt.authorization.exception.CommonException;

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
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getHttpCode()));
    }
}
