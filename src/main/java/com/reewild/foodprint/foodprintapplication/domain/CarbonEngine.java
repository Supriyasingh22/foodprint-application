package com.reewild.foodprint.foodprintapplication.domain;

import java.util.List;

import com.reewild.foodprint.foodprintapplication.dto.EstimateResponse;
import com.reewild.foodprint.foodprintapplication.dto.IngredientDTO;

public class CarbonEngine {

    public static EstimateResponse score(String dish, List<String> ingredients) {
        // Assign the same dummy carbon value to all ingredients
        double dummyCarbon = 1.0;

        List<IngredientDTO> enrichedIngredients = ingredients.stream()
            .map(ingredient -> new IngredientDTO(ingredient, dummyCarbon)) //asssign each ingredient dummyCarbon
            .toList();

        // total carbon = dummyCarbon * number of ingredients
        double totalCarbon = enrichedIngredients.size() * dummyCarbon;

        return new EstimateResponse(dish, totalCarbon, enrichedIngredients);
    }

}
