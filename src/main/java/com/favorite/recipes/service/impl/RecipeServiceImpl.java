package com.favorite.recipes.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.favorite.recipes.entity.Recipe;
import com.favorite.recipes.exception.DuplicateRecordErrorException;
import com.favorite.recipes.exception.ErrorSavingRecordException;
import com.favorite.recipes.exception.ResourceNotFoundException;
import com.favorite.recipes.repository.RecipeRepository;
import com.favorite.recipes.service.RecipeService;
import com.favorite.recipes.utils.CommonUtils;
import com.favorite.recipes.utils.RecipeSearchSpecification;

import jakarta.transaction.Transactional;
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
    @CacheEvict(cacheNames = "favorite_recipes", allEntries = true)
    @Transactional
    public Recipe createRecipe(Recipe recipe) {

        if (recipeRepository.existsByNameIgnoreCase(recipe.getName())) {

            throw new DuplicateRecordErrorException();
        }

        try {

            /** Adding UUID as a primary key */
            recipe.setId(CommonUtils.generateUUID());
            return recipeRepository.save(recipe);

        } catch (Exception e) {

            log.error(e.toString(), e);
            throw new ErrorSavingRecordException();

        }
    }
    
    @Override
    @Cacheable(cacheNames = "favorite_recipes", key = "#id")
    public Optional<Recipe> getRecipeById(String id) {
        
        try {
            
            return recipeRepository.findById(id);
            
        } catch (Exception e) {
            
            log.error(e.toString(), e);
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @Cacheable(cacheNames = "favorite_recipes")
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

    @Override
    @CacheEvict(cacheNames = "favorite_recipes", allEntries = true)
    @Transactional
    public Recipe updateRecipe(String id, Recipe recipe) {

        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }

        try {

            recipe.setId(id);
            return recipeRepository.save(recipe);

        } catch (Exception e) {

            log.error(e.toString(), e);
            throw new ErrorSavingRecordException();
        }
    }

    @Override
    @CacheEvict(cacheNames = "favorite_recipes", allEntries = true)
    @Transactional
    public Boolean deleteRecipe(String id) {

        try {

            if (recipeRepository.existsById(id)) {

                recipeRepository.deleteById(id);

                return true;

            } else {

                return false;
            }

        } catch (Exception e) {

            log.error(e.toString(), e);
            throw new ResourceNotFoundException();
        }

    }

   

}
