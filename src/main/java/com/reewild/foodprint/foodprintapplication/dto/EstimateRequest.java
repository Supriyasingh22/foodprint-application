package com.reewild.foodprint.foodprintapplication.dto;

import jakarta.validation.constraints.NotBlank;

public class EstimateRequest {

    @NotBlank
    private String dish;

    // Default constructor (needed for Spring)
    public EstimateRequest() {}

    // Parameterized constructor
    public EstimateRequest(String dish) {
        this.dish = dish;
    }

    // Getter
    public String getDish() {
        return dish;
    }

    // Setter
    public void setDish(String dish) {
        this.dish = dish;
    }
}
