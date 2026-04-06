package Image_generator.akash.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the OpenAI API returns an error or is unreachable.
 */
@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class OpenAiException extends RuntimeException {

    private final int statusCode;

    public OpenAiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public OpenAiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 502;
    }

    public int getStatusCode() {
        return statusCode;
    }
}