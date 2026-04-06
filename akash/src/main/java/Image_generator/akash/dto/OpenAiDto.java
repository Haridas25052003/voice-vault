package Image_generator.akash.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Internal DTOs mapping to/from OpenAI's Images API schema.
 * See: https://platform.openai.com/docs/api-reference/images/create
 */
public class OpenAiDto {

    // ─── Request ───────────────────────────────────────────────────────────────

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageRequest {
        private String model;
        private String prompt;
        private int n;
        private String size;
        private String quality;

        /** "url" or "b64_json" */
        @JsonProperty("response_format")
        private String responseFormat;
    }

    // ─── Response ──────────────────────────────────────────────────────────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageResponse {
        private long created;
        private List<ImageData> data;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageData {
        private String url;

        @JsonProperty("b64_json")
        private String b64Json;

        @JsonProperty("revised_prompt")
        private String revisedPrompt;
    }

    // ─── OpenAI Error Response ─────────────────────────────────────────────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenAiErrorResponse {
        private OpenAiError error;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OpenAiError {
            private String message;
            private String type;
            private String code;
        }
    }
}