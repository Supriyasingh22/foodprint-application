package com.reewild.foodprint.foodprintapplication.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OpenAiVision implements AiVisionProvider {

    private static final String COMPLETIONS_URL = "https://api.openai.com/v1/chat/completions";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String apiKey;
    private final HttpClient http;
    private final String model;

    public OpenAiVision(String apiKey) {
        this(apiKey, "gpt-4o");
    }

    public OpenAiVision(String apiKey, String model) {
        this.apiKey = apiKey;
        this.model = model;
        this.http = HttpClient.newHttpClient();
    }

    private static String systemVisionPrompt() {
        return "You are a food vision assistant. Identify the most likely dish and its ingredients from the image. "
             + "Return ONLY JSON with this schema: {\"dish\": string, "
             + "\"ingredients\":[{\"name\":string,\"confidence\":number}], "
             + "\"assumptions\":[string]}";
    }

    @Override
    public VisionResult inferFromImageBase64(String base64) {
        try {
            ObjectNode req = MAPPER.createObjectNode();
            req.put("model", model);

            ArrayNode messages = MAPPER.createArrayNode();

            ObjectNode sysMsg = MAPPER.createObjectNode();
            sysMsg.put("role", "system");
            sysMsg.put("content", systemVisionPrompt());
            messages.add(sysMsg);

            ObjectNode userMsg = MAPPER.createObjectNode();
            userMsg.put("role", "user");

            ArrayNode contentArray = MAPPER.createArrayNode();

            ObjectNode textPart = MAPPER.createObjectNode();
            textPart.put("type", "text");
            textPart.put("text", "What dish is this and what are the ingredients?");
            contentArray.add(textPart);

            ObjectNode imagePart = MAPPER.createObjectNode();
            imagePart.put("type", "image_url");
            ObjectNode imageUrl = MAPPER.createObjectNode();
            imageUrl.put("url", "data:image/jpeg;base64," + base64);
            imagePart.set("image_url", imageUrl);
            contentArray.add(imagePart);

            userMsg.set("content", contentArray);
            messages.add(userMsg);

            req.set("messages", messages);

            HttpRequest httpReq = HttpRequest.newBuilder(URI.create(COMPLETIONS_URL))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(req.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> res = http.send(httpReq, HttpResponse.BodyHandlers.ofString());

            System.out.println("🔎 OpenAI raw response: " + res.body());

            JsonNode parsed = MAPPER.readTree(res.body());

            // Extract assistant message
            String content = parsed.at("/choices/0/message/content").asText();
            if (content == null || content.isBlank()) {
                throw new RuntimeException("No content in OpenAI response: " + res.body());
            }

            // Clean to valid JSON
            String jsonString = content.trim();
            if (!jsonString.startsWith("{")) {
                int start = jsonString.indexOf('{');
                int end = jsonString.lastIndexOf('}');
                if (start >= 0 && end > start) {
                    jsonString = jsonString.substring(start, end + 1);
                }
            }

            JsonNode inner = MAPPER.readTree(jsonString);

            String dish = inner.path("dish").asText(null);

            List<AiNlpProvider.IngredientGuess> ingredients = new ArrayList<>();
            for (JsonNode n : inner.withArray("ingredients")) {
                ingredients.add(new AiNlpProvider.IngredientGuess(
                        n.path("name").asText(),
                        n.path("confidence").asDouble(0.7)
                ));
            }

            return new VisionResult(dish, ingredients);

        } catch (Exception e) {
            e.printStackTrace();
            return new VisionResult(
                    null,
                    List.of(new AiNlpProvider.IngredientGuess("Vegetables", 0.5))
            );
        }
    }
}
