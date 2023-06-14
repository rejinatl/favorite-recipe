package com.favorite.recipes.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.favorite.recipes.controller.RecipeController;
import com.favorite.recipes.dto.request.RecipeSearchRequest;
import com.favorite.recipes.entity.Recipe;
import com.favorite.recipes.exception.ErrorSavingRecordException;
import com.favorite.recipes.exception.ResourceNotFoundException;
import com.favorite.recipes.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RecipeControllerImpl implements RecipeController {
    
    private RecipeService recipeService;
    
    public RecipeService getRecipeService() {
        return recipeService;
    }
    
    @Autowired
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public ResponseEntity<Recipe> createRecipe(Recipe recipe) {
       
        try {
            
            Recipe savedRecipe = recipeService.createRecipe(recipe);
            
            return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
            
        } catch (ErrorSavingRecordException e) {
            
            return new ResponseEntity<>(recipe, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Recipe>> getRecipe(String isVegetarian, Integer servings, String includeIngredient,
            String excludeIngredient, String instructionContains) {
       
        try {
            
            List<Recipe> getAllRecipeWithSearchCriteria  = recipeService.getRecipe(isVegetarian, servings,
                    includeIngredient, excludeIngredient, instructionContains);
            
            return new ResponseEntity<>(getAllRecipeWithSearchCriteria, HttpStatus.OK);
            
        } catch (ResourceNotFoundException e) {
            
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    

}
