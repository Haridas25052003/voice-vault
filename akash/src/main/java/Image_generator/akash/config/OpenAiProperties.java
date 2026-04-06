package Image_generator.akash.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Strongly-typed configuration class for OpenAI API properties.
 * All values are loaded from application.properties / environment variables.
 */
@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "openai.api")
public class OpenAiProperties {

    /**
     * OpenAI API key. Must be set via environment variable OPENAI_API_KEY.
     * Never hardcode this value.
     */
    @NotBlank(message = "OpenAI API key must not be blank")
    private String key;

    /** Base URL for the OpenAI REST API */
    private String baseUrl = "https://api.openai.com/v1";

    /** DALL·E model to use for image generation */
    private String imageModel = "dall-e-3";

    /** Image resolution (e.g., 1024x1024, 1792x1024, 1024x1792) */
    private String imageSize = "1024x1024";

    /** Image quality: "standard" or "hd" */
    private String imageQuality = "hd";

    /** HTTP client timeout in seconds */
    @Positive
    private int timeoutSeconds = 60;
}