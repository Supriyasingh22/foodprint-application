package com.reewild.foodprint.foodprintapplication.web;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.reewild.foodprint.foodprintapplication.ai.AiNlpProvider;
import com.reewild.foodprint.foodprintapplication.ai.AiVisionProvider;
import com.reewild.foodprint.foodprintapplication.domain.CarbonEngine;
import com.reewild.foodprint.foodprintapplication.dto.EstimateRequest;
import com.reewild.foodprint.foodprintapplication.dto.EstimateResponse;

import java.util.Base64;

@RestController
@RequestMapping("/v1")
public class EstimateController {

    private final AiNlpProvider nlp;
    private final AiVisionProvider vision;

    public EstimateController(AiNlpProvider nlp, AiVisionProvider vision) {
        this.nlp = nlp;
        this.vision = vision;
    }

    /**
     * Estimate carbon footprint based on a dish name
     */
    @PostMapping(value = "/estimate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EstimateResponse estimate(@RequestBody @Valid EstimateRequest request) {
        var nlpResult = nlp.inferIngredientsFromDish(request.getDish());
        // Pass only ingredients to CarbonEngine
       var ingredientNames = nlpResult.ingredients().stream()
                         .map(ing -> ing.name()) // extract name from IngredientGuess
                         .toList();
        // Extra validation check for gibberish input 
        if (ingredientNames.isEmpty()) {
        throw new IllegalArgumentException("That does not look like a proper dish. Please try again with a valid dish name.");
        }

        return CarbonEngine.score(request.getDish(), ingredientNames);
    }

    /**
     * Estimate carbon footprint from an image of a dish
     */
   @PostMapping(value = "/estimate/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EstimateResponse estimateImage(@RequestPart("image") MultipartFile image) throws Exception {
        if (image.isEmpty()) throw new IllegalArgumentException("Image is empty");

        var base64 = Base64.getEncoder().encodeToString(image.getBytes());
        var visionResult = vision.inferFromImageBase64(base64);

        var ingredientNames = visionResult.ingredients().stream()
                                .map(ing -> ing.name())
                                .toList();
        
        // Extra validation check for gibberish input 
        if (ingredientNames.isEmpty()) {
        throw new IllegalArgumentException("That does not look like a proper dish. Please try again with a valid dish name.");
        }

        return CarbonEngine.score(visionResult.dish(), ingredientNames);
    }
   
    // Health check endpoint
    @GetMapping("/healthz")
    public String health() {
        return "ok";
    }
}
