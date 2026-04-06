package Image_generator.akash.service;

import Image_generator.akash.config.OpenAiProperties;
import Image_generator.akash.dto.ImageGenerationRequest;
import Image_generator.akash.dto.ImageGenerationResponse;
import Image_generator.akash.dto.OpenAiDto;
import Image_generator.akash.exception.OpenAiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Service layer responsible for communicating with the OpenAI Images API.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Build and enrich prompts (append style hints)</li>
 *   <li>Call the DALL·E image generation endpoint</li>
 *   <li>Map OpenAI response to our internal DTO</li>
 *   <li>Translate OpenAI errors into domain exceptions</li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageGenerationService {

    private static final String IMAGES_ENDPOINT = "/images/generations";

    private final RestTemplate openAiRestTemplate;
    private final OpenAiProperties openAiProperties;

    /**
     * Generates an image using OpenAI DALL·E based on the given request.
     *
     * @param request validated request containing the prompt, style, and optional size
     * @return response containing the image URL and metadata
     * @throws OpenAiException if the OpenAI API call fails for any reason
     */
    public ImageGenerationResponse generateImage(ImageGenerationRequest request) {
        String enrichedPrompt = buildEnrichedPrompt(request);
        String imageSize = resolveImageSize(request.getSize());

        log.info("Generating image | model={} | size={} | promptLength={}",
                openAiProperties.getImageModel(), imageSize, enrichedPrompt.length());
        log.debug("Full prompt: {}", enrichedPrompt);

        OpenAiDto.ImageRequest openAiRequest = OpenAiDto.ImageRequest.builder()
                .model(openAiProperties.getImageModel())
                .prompt(enrichedPrompt)
                .n(1)
                .size(imageSize)
                .quality(openAiProperties.getImageQuality())
                .responseFormat("url")
                .build();

        OpenAiDto.ImageResponse openAiResponse = callOpenAiApi(openAiRequest);

        if (openAiResponse.getData() == null || openAiResponse.getData().isEmpty()) {
            throw new OpenAiException("OpenAI returned an empty image list", HttpStatus.BAD_GATEWAY.value());
        }

        OpenAiDto.ImageData imageData = openAiResponse.getData().get(0);
        log.info("Image generated successfully | url preview: {}...",
                imageData.getUrl() != null ? imageData.getUrl().substring(0, Math.min(60, imageData.getUrl().length())) : "null");

        return ImageGenerationResponse.builder()
                .imageUrl(imageData.getUrl())
                .revisedPrompt(imageData.getRevisedPrompt())
                .model(openAiProperties.getImageModel())
                .size(imageSize)
                .generatedAt(System.currentTimeMillis())
                .build();
    }

    // ─── Private Helpers ───────────────────────────────────────────────────────

    /**
     * Appends style instructions to the base prompt for richer output.
     */
    private String buildEnrichedPrompt(ImageGenerationRequest request) {
        StringBuilder sb = new StringBuilder(request.getPrompt().trim());
        if (StringUtils.hasText(request.getStyle())) {
            sb.append(". Style: ").append(request.getStyle().trim());
        }
        return sb.toString();
    }

    /**
     * Resolves image size: uses the request's value if valid, otherwise falls back to config.
     */
    private String resolveImageSize(String requestedSize) {
        if (StringUtils.hasText(requestedSize) &&
                (requestedSize.equals("1024x1024")
                        || requestedSize.equals("1792x1024")
                        || requestedSize.equals("1024x1792"))) {
            return requestedSize;
        }
        return openAiProperties.getImageSize();
    }

    /**
     * Executes the HTTP call to OpenAI and handles error responses.
     */
    private OpenAiDto.ImageResponse callOpenAiApi(OpenAiDto.ImageRequest request) {
        String url = openAiProperties.getBaseUrl() + IMAGES_ENDPOINT;

        try {
            ResponseEntity<OpenAiDto.ImageResponse> response =
                    openAiRestTemplate.postForEntity(url, request, OpenAiDto.ImageResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
            throw new OpenAiException("Unexpected response from OpenAI API", response.getStatusCode().value());

        } catch (HttpClientErrorException ex) {
            // 4xx errors — content policy, invalid request, quota exceeded, etc.
            log.error("OpenAI client error [status={}]: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            String message = extractOpenAiErrorMessage(ex.getResponseBodyAsString());
            throw new OpenAiException(message, ex.getStatusCode().value());

        } catch (HttpServerErrorException ex) {
            // 5xx errors — OpenAI service outage
            log.error("OpenAI server error [status={}]: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new OpenAiException("OpenAI service is temporarily unavailable. Please try again later.",
                    ex.getStatusCode().value());

        } catch (ResourceAccessException ex) {
            // Network-level errors: timeout, DNS failure, etc.
            log.error("Network error reaching OpenAI API", ex);
            throw new OpenAiException(
                    "Unable to reach OpenAI API. Please check your network connection or try again.", 504);
        }
    }

    /**
     * Attempts to extract a human-readable error message from OpenAI's JSON error body.
     * Falls back to a generic message if parsing fails.
     */
    private String extractOpenAiErrorMessage(String responseBody) {
        try {
            // Simple string check — avoids importing an extra ObjectMapper dependency here
            if (responseBody.contains("\"message\"")) {
                int start = responseBody.indexOf("\"message\"") + 11;
                int end = responseBody.indexOf("\"", start);
                if (end > start) {
                    return responseBody.substring(start, end);
                }
            }
        } catch (Exception ignored) {
            // Fall through to generic message
        }
        return "OpenAI API request failed. This may be due to content policy, quota limits, or an invalid request.";
    }
}