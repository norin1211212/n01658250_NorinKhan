package com.vojtechruzicka.springaidemo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // Enable CORS for frontend access
public class AiController {

    private final ChatService chatService;
    private final ImageService imageService;

    public AiController(ChatService chatService, ImageService imageService) {
        this.chatService = chatService;
        this.imageService = imageService;
    }

    @GetMapping("ask-ai")
    public ResponseEntity<String> askAi(@RequestParam("prompt") String prompt) {
        try {
            if (prompt == null || prompt.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Prompt cannot be empty");
            }

            String response = chatService.queryAi(prompt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating response: " + e.getMessage());
        }
    }

    @GetMapping("generate-image")
    public void generateImage(HttpServletResponse response, @RequestParam("prompt") String prompt) throws IOException {
        try {
            if (prompt == null || prompt.trim().isEmpty()) {
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Prompt cannot be empty");
                return;
            }

            ImageResponse imageResponse = imageService.generateImage(prompt);

            // Get URL of the generated image
            String imageUrl = imageResponse.getResult().getOutput().getUrl();

            // Send redirect to the image URL
            response.sendRedirect(imageUrl);
        } catch (Exception e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error generating image: " + e.getMessage());
        }
    }

    @GetMapping("city-guide")
    public ResponseEntity<String> cityGuide(@RequestParam("city") String city,
                                            @RequestParam("interest") String interest) {
        try {
            if (city == null || city.trim().isEmpty() ||
                    interest == null || interest.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("City and interest parameters are required");
            }

            String response = chatService.getCityGuide(city, interest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating city guide: " + e.getMessage());
        }
    }

    // Add these debug methods to your AiController class

    @GetMapping("generate-image-debug")
    public ResponseEntity<String> generateImageDebug(@RequestParam("prompt") String prompt) {
        try {
            if (prompt == null || prompt.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Prompt cannot be empty");
            }

            System.out.println("Generating image for prompt: " + prompt);
            ImageResponse imageResponse = imageService.generateImage(prompt);

            String imageUrl = imageResponse.getResult().getOutput().getUrl();
            System.out.println("Generated image URL: " + imageUrl);

            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            System.err.println("Error generating image: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating image: " + e.getMessage());
        }
    }

    @GetMapping("generate-image-json")
    public ResponseEntity<Map<String, String>> generateImageJson(@RequestParam("prompt") String prompt) {
        try {
            if (prompt == null || prompt.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Prompt cannot be empty"));
            }

            ImageResponse imageResponse = imageService.generateImage(prompt);
            String imageUrl = imageResponse.getResult().getOutput().getUrl();

            return ResponseEntity.ok(Map.of(
                    "success", "true",
                    "imageUrl", imageUrl,
                    "prompt", prompt
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", e.getMessage(),
                            "success", "false"
                    ));
        }
    }
}