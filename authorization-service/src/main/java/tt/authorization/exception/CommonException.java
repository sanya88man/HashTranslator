package tt.authorization.exception;

import lombok.Data;
import lombok.Getter;

/**
 * Common runtime exception.
 */
@Getter
public class CommonException extends RuntimeException {
    private String message;
    private int httpCode;
    public CommonException(String message, int httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }
}
