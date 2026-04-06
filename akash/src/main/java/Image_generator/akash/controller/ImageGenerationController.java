package Image_generator.akash.controller;

import Image_generator.akash.dto.ImageGenerationRequest;
import Image_generator.akash.dto.ImageGenerationResponse;
import Image_generator.akash.service.ImageGenerationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing the image generation API.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>POST /api/generate-image — Generate an image from a text prompt</li>
 * </ul>
 *
 * <p>All validation errors, business exceptions, and unexpected errors are handled
 * centrally by {@link com.aiimage.generator.exception.GlobalExceptionHandler}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageGenerationController {

    private final ImageGenerationService imageGenerationService;

    /**
     * Accepts a text prompt and optional style, then returns a generated image URL.
     *
     * @param request validated request body
     * @return 200 OK with image URL and metadata
     */
    @PostMapping("/generate-image")
    public ResponseEntity<ImageGenerationResponse> generateImage(
            @Valid @RequestBody ImageGenerationRequest request) {

        log.info("Received image generation request | prompt length: {} | style: {}",
                request.getPrompt().length(), request.getStyle());

        ImageGenerationResponse response = imageGenerationService.generateImage(request);

        log.info("Image generation completed | model: {} | size: {}",
                response.getModel(), response.getSize());

        return ResponseEntity.ok(response);
    }

    /**
     * Simple health ping for load balancer / uptime checks.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}