package com.reewild.foodprint.foodprintapplication.dto;

import java.util.List;

public class EstimateResponse {
    private String dish;
    private Double estimatedCarbonKg;
    private List<IngredientDTO> ingredients;

    // Constructor
    public EstimateResponse(String dish, Double estimatedCarbonKg,
                            List<IngredientDTO> ingredients) {
        this.dish = dish;
        this.estimatedCarbonKg = estimatedCarbonKg;
        this.ingredients = ingredients;
    }

    // Getters
    public String getDish() {
        return dish;
    }

    public Double getEstimatedCarbonKg() {
        return estimatedCarbonKg;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    // Setters
    public void setDish(String dish) {
        this.dish = dish;
    }

    public void setEstimatedCarbonKg(Double estimatedCarbonKg) {
        this.estimatedCarbonKg = estimatedCarbonKg;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
