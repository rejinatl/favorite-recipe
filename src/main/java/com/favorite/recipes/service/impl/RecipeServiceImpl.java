package com.favorite.recipes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.favorite.recipes.dto.request.RecipeSearchRequest;
import com.favorite.recipes.entity.Recipe;
import com.favorite.recipes.exception.ErrorSavingRecordException;
import com.favorite.recipes.exception.ResourceNotFoundException;
import com.favorite.recipes.repository.RecipeRepository;
import com.favorite.recipes.service.RecipeService;
import com.favorite.recipes.utils.RecipeSearchSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    
    private RecipeSearchSpecification recipeSearchSpecification;
    
    public RecipeRepository getRecipeRepository() {
        return recipeRepository;
    }
    
    @Autowired
    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    
    public RecipeSearchSpecification getRecipeSearchSpecification() {
        return recipeSearchSpecification;
    }

    @Autowired
    public void setRecipeSearchSpecification(RecipeSearchSpecification recipeSearchSpecification) {
        this.recipeSearchSpecification = recipeSearchSpecification;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        
        try {
             
            return recipeRepository.save(recipe);
            
        }catch (Exception e) {
           
            log.error(e.toString(), e);
            throw new ErrorSavingRecordException();
            
        }
    }

    @Override
    public List<Recipe> getRecipe(String isVegetarian, Integer servings, String includeIngredient,
            String excludeIngredient, String instructionContains) {
        
        try {
            
            return recipeRepository.findAll(recipeSearchSpecification.searchRecipe(isVegetarian, servings,
                    includeIngredient, excludeIngredient, instructionContains));
            
        } catch (Exception e) {

            log.error(e.toString(), e);
            throw new ResourceNotFoundException();
           
        }
    }

}
