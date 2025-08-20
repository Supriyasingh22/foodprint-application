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
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OpenAiNlp implements AiNlpProvider {

    private static final String RESPONSES_URL = "https://api.openai.com/v1/responses";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String apiKey;
    private final HttpClient http;
    private final String model;

    public OpenAiNlp(String apiKey) {
        this(apiKey, "gpt-4o-mini");
    }

    public OpenAiNlp(String apiKey, String model) {
        this.apiKey = apiKey;
        this.model = model;
        this.http = HttpClient.newHttpClient();
    }

    private static String systemDishPrompt() {
        return "You are a culinary analyst. Given a DISH NAME, return ONLY JSON with this exact schema: "
             + "{ \"ingredients\":[{\"name\":string,\"confidence\":number}], "
             + "\"assumptions\":[string] } "
             + "No extra text. Confidence in [0,1]. Single serving.";
    }

    @Override
    public NlpResult inferIngredientsFromDish(String dish) {
        try {
            // Build request JSON
            ObjectNode req = MAPPER.createObjectNode();
            req.put("model", model);

            // User message
            ObjectNode userMsg = MAPPER.createObjectNode();
            userMsg.put("role", "user");

            var contentArray = MAPPER.createArrayNode();
            ObjectNode textNode = MAPPER.createObjectNode();
            textNode.put("type", "input_text"); // correct type
            textNode.put("text", systemDishPrompt() + " DISH: " + dish);
            contentArray.add(textNode);

            userMsg.set("content", contentArray);
            req.set("input", MAPPER.createArrayNode().add(userMsg));

            // Send request
            HttpRequest httpReq = HttpRequest.newBuilder(URI.create(RESPONSES_URL))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(req.toString(), StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> res = http.send(httpReq, HttpResponse.BodyHandlers.ofString());

            System.out.println("OpenAI raw response: " + res.body());

            JsonNode parsed = MAPPER.readTree(res.body());

            // Extract text of the first output message
            JsonNode outputTextNode = parsed.at("/output/0/content/0/text");
            if (outputTextNode.isMissingNode()) {
                throw new RuntimeException("No output from OpenAI API");
            }

            // Parse the JSON text returned by the model
            String jsonString = outputTextNode.asText();
            JsonNode inner = MAPPER.readTree(jsonString);

            // Extract ingredients
            List<IngredientGuess> ingredients = new ArrayList<>();
            for (JsonNode n : inner.withArray("ingredients")) {
                ingredients.add(new IngredientGuess(
                        n.path("name").asText(),
                        n.path("confidence").asDouble(0.7)
                ));
            }

            // Extract assumptions
            List<String> assumptions = new ArrayList<>();
            for (JsonNode n : inner.withArray("assumptions")) {
                assumptions.add(n.asText());
            }

            return new NlpResult(ingredients, assumptions);

        } catch (Exception e) {
            return new NlpResult(
                    List.of(new IngredientGuess("Vegetables", 0.5)),
                    List.of("Fallback after error: " + e.getMessage())
            );
        }
    }
}
