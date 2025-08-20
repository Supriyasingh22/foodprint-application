package com.reewild.foodprint.foodprintapplication.ai;

import java.util.List;

public interface AiVisionProvider {

    record VisionResult(
            String dish,
            List<AiNlpProvider.IngredientGuess> ingredients
    ) {}

    VisionResult inferFromImageBase64(String base64);
}
