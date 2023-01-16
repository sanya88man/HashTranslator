package tt.authorization.exception;

import lombok.Getter;

/**
 * Common runtime exception.
 */
@Getter
public class CommonException extends RuntimeException {
    private int httpCode;

    public CommonException(String message, int httpCode) {
        super(message);
        this.httpCode = httpCode;
    }
}
