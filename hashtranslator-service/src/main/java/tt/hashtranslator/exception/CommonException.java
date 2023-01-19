package tt.hashtranslator.exception;

import lombok.Getter;

/**
 * Common runtime exception.
 */
@Getter
public class CommonException extends RuntimeException {
    private int httpCode;

    /**
     * Creates common exception with error message and http status code.
     *
     * @param message error message
     * @param httpCode http status code
     */
    public CommonException(String message, int httpCode) {
        super(message);
        this.httpCode = httpCode;
    }
}
