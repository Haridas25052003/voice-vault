package Image_generator.akash.exception;

import Image_generator.akash.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Centralized exception handler for all REST controllers.
 * Converts exceptions into structured ApiErrorResponse JSON objects.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles Bean Validation failures (e.g., blank prompt, oversized input).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        List<ApiErrorResponse.FieldError> fieldErrors = result.getFieldErrors().stream()
                .map(fe -> new ApiErrorResponse.FieldError(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        log.warn("Validation failed: {}", fieldErrors);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("VALIDATION_FAILED")
                .message("Request validation failed. Please check your input.")
                .fieldErrors(fieldErrors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles OpenAI API errors (rate limits, content policy violations, etc.).
     */
    @ExceptionHandler(OpenAiException.class)
    public ResponseEntity<ApiErrorResponse> handleOpenAiException(OpenAiException ex) {
        log.error("OpenAI API error [status={}]: {}", ex.getStatusCode(), ex.getMessage());

        HttpStatus httpStatus = ex.getStatusCode() == 429
                ? HttpStatus.TOO_MANY_REQUESTS
                : HttpStatus.BAD_GATEWAY;

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(httpStatus.value())
                .error("OPENAI_API_ERROR")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

    /**
     * Fallback handler for all unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);

        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred. Please try again later.")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}