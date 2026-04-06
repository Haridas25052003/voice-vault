package Image_generator.akash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Standardized error response returned for all API failures.
 * Follows RFC 7807 Problem Details conventions (simplified).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    /** HTTP status code */
    private int status;

    /** Short error code (e.g., VALIDATION_FAILED, OPENAI_ERROR) */
    private String error;

    /** Human-readable error message */
    private String message;

    /** Timestamp of the error */
    @Builder.Default
    private String timestamp = Instant.now().toString();

    /** Field-level validation errors (populated for 400 responses) */
    private List<FieldError> fieldErrors;

    @Data
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
    }
}