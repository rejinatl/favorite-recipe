package com.favorite.recipes.service;

import java.util.List;
import java.util.Optional;

import com.favorite.recipes.entity.Recipe;

public interface RecipeService {
    
    public Recipe createRecipe(Recipe recipe);
    
    public Recipe updateRecipe(String id, Recipe recipe);
    
    public Boolean deleteRecipe(String id);
    
    public Optional<Recipe> getRecipeById(String id);
    
    public List<Recipe> getRecipe(String isVegetarian, Integer servings, String includeIngredient,
            String excludeIngredient, String instructionContains);

}
