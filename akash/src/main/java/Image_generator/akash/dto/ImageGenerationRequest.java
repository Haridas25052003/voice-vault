package Image_generator.akash.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the incoming image generation request from the client.
 * Validated before reaching the service layer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerationRequest {

    /**
     * The text prompt describing the image to be generated.
     * OpenAI recommends detailed, descriptive prompts for best results.
     */
    @NotBlank(message = "Prompt must not be blank")
    @Size(min = 3, max = 4000, message = "Prompt must be between 3 and 4000 characters")
    private String prompt;

    /**
     * Optional style hint appended to the prompt (e.g., "realistic", "anime", "oil painting").
     * Leave blank to let DALL·E decide.
     */
    @Size(max = 200, message = "Style must not exceed 200 characters")
    private String style;

    /**
     * Desired image size. Supported: 1024x1024, 1792x1024, 1024x1792.
     * Defaults to application.properties value if null.
     */
    private String size;
}