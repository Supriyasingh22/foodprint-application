package com.reewild.foodprint.foodprintapplication.ai;

 import java.util.List;
 public interface AiNlpProvider {
    record IngredientGuess(String name, Double confidence) {}
    record NlpResult(List<IngredientGuess> ingredients, List<String>
    assumptions) {}
    NlpResult inferIngredientsFromDish(String dish);
 }
