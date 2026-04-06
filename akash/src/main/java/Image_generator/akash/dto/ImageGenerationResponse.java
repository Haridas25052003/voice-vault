package Image_generator.akash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO returned to the client after a successful image generation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerationResponse {

    /** The URL of the generated image hosted on OpenAI's CDN (valid ~1 hour) */
    private String imageUrl;

    /** The revised/enhanced prompt that DALL·E actually used */
    private String revisedPrompt;

    /** The model used for generation */
    private String model;

    /** Image size used */
    private String size;

    /** Timestamp (epoch ms) of when the image was generated */
    private long generatedAt;
}