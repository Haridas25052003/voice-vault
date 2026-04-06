package Image_generator.akash.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Application-wide web and HTTP client configuration.
 * Configures RestTemplate with proper timeouts and CORS settings.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final OpenAiProperties openAiProperties;

    /**
     * Configures a RestTemplate bean with:
     * - Authorization header injected automatically for OpenAI calls
     * - Connection and read timeouts to prevent hung requests
     */
    @Bean
    public RestTemplate openAiRestTemplate() {
        log.info("Configuring OpenAI RestTemplate with timeout: {}s", openAiProperties.getTimeoutSeconds());

        // Spring Boot 3.x: use SimpleClientHttpRequestFactory for timeouts
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15_000);                                          // 15 seconds
        factory.setReadTimeout(openAiProperties.getTimeoutSeconds() * 1000);        // from config

        RestTemplate restTemplate = new RestTemplate(factory);

        // Inject OpenAI auth header on every request
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Authorization", "Bearer " + openAiProperties.getKey());
            request.getHeaders().set("Content-Type", "application/json");
            return execution.execute(request, body);
        });

        return restTemplate;
    }

    /**
     * CORS configuration — restrict to your frontend domain in production.
     * Current setting allows all origins for local development only.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")   // ⚠️ Tighten this to your domain in production
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}