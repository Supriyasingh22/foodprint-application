package com.reewild.foodprint.foodprintapplication.dto;

 public class IngredientDTO {
    private String name;
    private Double carbonKg;

    public IngredientDTO(String name, Double carbonKg) {
        this.name = name;
        this.carbonKg = carbonKg;
    }

    // getters
    public String getName() { return name; }
    public Double getCarbonKg() { return carbonKg; }

    // setters (optional, only if you want mutability)
    public void setName(String name) { this.name = name; }
    public void setCarbonKg(Double carbonKg) { this.carbonKg = carbonKg; }
}