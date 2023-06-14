package com.favorite.recipes.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecipeSearchRequest {

    private String isVegetarian;
    private Integer servings;
    private String includeIngredient;
    private String excludeIngredient;
    private String instructionContains;
}
