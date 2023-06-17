package com.favorite.recipes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.jpa.domain.Specification;

import com.favorite.recipes.entity.Ingredient;
import com.favorite.recipes.entity.Recipe;
import com.favorite.recipes.exception.DuplicateRecordErrorException;
import com.favorite.recipes.exception.ResourceNotFoundException;
import com.favorite.recipes.repository.RecipeRepository;
import com.favorite.recipes.service.impl.RecipeServiceImpl;
import com.favorite.recipes.utils.RecipeSearchSpecification;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RecipeServiceTests {
    
    @Mock
    RecipeRepository recipeRepository;
    
    @InjectMocks
    RecipeServiceImpl recipeService;
    
    @Mock
    private RecipeSearchSpecification recipeSearchSpecification;

    @DisplayName("Create Recipe")
    @Test
    public void createRecipe_Return_Recipe() throws Exception {
        
        Recipe recipe = getDummyRecipe();
        
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        Recipe recipeFromDB = recipeService.createRecipe(recipe);
        assertThat(recipeFromDB).isNotNull();
        
    }
    
    @DisplayName("create recipe - name already present return DuplicateRecordErrorException")
    @Test
    public void createRecipe_Return_DuplicateRecordErrorException() throws Exception {
        
        when(recipeRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);
        Recipe recipe = getDummyRecipe();
        assertThrows(DuplicateRecordErrorException.class, ()-> recipeService.createRecipe(recipe));
        
    }
    
    @DisplayName("getRecipe with return collection of recipe")
    @Test
    public void getRecipe_Return_Recipt_List() throws Exception {
        
        List<Recipe> recipes = new ArrayList<>();
        
        Recipe recipe1 = getDummyRecipe();
        Recipe recipe2 = getDummyRecipe2();
        recipes.addAll(Arrays.asList(recipe1,recipe2));
        
        when(recipeRepository.findAll(any(Specification.class))).thenReturn(recipes);

        List<Recipe> recipesFromDB = recipeService.getRecipe("no", 10,"chicken", "butter","instruction");

        assertThat(recipesFromDB).isNotNull();
        
    }
    
    @DisplayName("GetRecipe by specfying ID")
    @Test
    public void getRecipeById_Return_Recipt() throws Exception {
        
        Recipe recipe = getDummyRecipe();
        
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        Optional<Recipe> recipesFromDB = recipeService.getRecipeById("28c004e2-f2e7-4a48-90a9-cad60255fcad");

        assertThat(recipesFromDB).isNotNull();
        
    }
    
    @DisplayName("getRecipe with return collection of recipe")
    @Test
    public void getRecipe_Return_Empty_List() throws Exception {
        
        List<Recipe> recipes = new ArrayList<>();
        
        Recipe recipe1 = getDummyRecipe();
        Recipe recipe2 = getDummyRecipe2();
        recipes.addAll(Arrays.asList(recipe1,recipe2));
        when(recipeRepository.findAll()).thenReturn(Collections.emptyList());

        List<Recipe> recipesFromDB = recipeService.getRecipe("yes", 10,"butter", "","");

        assertThat(recipesFromDB).isEmpty();
        
    }
    
    @DisplayName("Return a Recipe by its ID")
    @Test
    public void getRecipeById_Return_Ok() throws Exception {

        Recipe recipe = getDummyRecipe();

        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe));

        Optional<Recipe> recipeOptional = recipeRepository.findById("28c004e2-f2e7-4a48-90a9-cad60255fc");

        assertThat(recipeOptional).isNotNull();

    }
    
    @DisplayName("updateRecipe and return Recipe")
    @Test
    public void updateRecipe_andReturn_Recipe() throws Exception {
        
        Recipe recipe1 = getDummyRecipe();
        
        when(recipeRepository.existsById(anyString())).thenReturn(true);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe1);
        
        Recipe recipeFromDB = recipeService.updateRecipe("28c004e2-f2e7-4a48-90a9-cad60255fcad",recipe1);
        assertThat(recipeFromDB).isNotNull();
        
        verify(recipeRepository).save(any(Recipe.class));
        
    }
    
    @DisplayName("updateRecipe and return ResourceNotFoundException")
    @Test
    public void updateRecipe_andReturn_ResourceNotFoundException() throws Exception {
        
        Recipe recipe1 = getDummyRecipe();
        
        when(recipeRepository.existsById(anyString())).thenReturn(false);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe1);
        
        assertThatThrownBy(() -> recipeService.updateRecipe("28c004e2-f2e7-4a48-90a9-cad60255fcad",recipe1))
        .isInstanceOf(ResourceNotFoundException.class);
        
    }
    
    
    @DisplayName("deleteRecipe return true")
    @Test
    public void deleteRecipe_Return_True() throws Exception {
        
        String id = "28c004e2-f2e7-4a48-90a9-cad60255fcad";
        
        when(recipeRepository.existsById(anyString())).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(anyString());
        
        recipeService.deleteRecipe(id);
        recipeService.deleteRecipe(id);

        verify(recipeRepository, times(2)).deleteById(id);
        
    }
    
    @DisplayName("deleteRecipe return false")
    @Test
    public void deleteRecipe_Return_False() throws Exception {
        
        
        Recipe recipe = getDummyRecipe();
        String id = "28c004e2-f2e7-4a48-90a9-cad60255fcad";
        
        when(recipeRepository.existsById(anyString())).thenReturn(false);
        doNothing().when(recipeRepository).deleteById(anyString());
        
        Boolean status = recipeService.deleteRecipe(id);
        assertFalse( status );
        assertThat(recipe).isNotNull();
        
    }
    
    
    private Recipe getDummyRecipe() {
        
        Recipe recipe =new Recipe();
        Set<Ingredient> ingredients = new HashSet<>();
        
        Ingredient in1 = getIngredient("Chicken");
        Ingredient in2 = getIngredient("OIL");
        Ingredient in3 = getIngredient("Chilli");
        Ingredient in4 = getIngredient("Potatoes");
        ingredients.addAll(Arrays.asList(in1,in2,in3,in4));
        
        recipe.setId("28c004e2-f2e7-4a48-90a9-cad60255fcad");
        recipe.setName("chicken curry");
        recipe.setIsVegetarian("no");
        recipe.setDescription("description");
        recipe.setInstruction("instruction");
        recipe.setServings(100);
        recipe.setIngredients(ingredients);
        
        return recipe;
    }
    
    private Recipe getDummyRecipe2() {
        
        Recipe recipe =new Recipe();
        Set<Ingredient> ingredients = new HashSet<>();
        
        Ingredient in1 = getIngredient("Lamb curry");
        Ingredient in2 = getIngredient("OIL");
        Ingredient in3 = getIngredient("Chilli");
        Ingredient in4 = getIngredient("Potatoes");
        ingredients.addAll(Arrays.asList(in1,in2,in3,in4));
        
        recipe.setId("28c004e2-f2e7-4a48-90a9-cad60255fddc");
        recipe.setName("Lamb curry");
        recipe.setIsVegetarian("no");
        recipe.setDescription("Lamb curry");
        recipe.setInstruction("Lamb curry");
        recipe.setServings(10);
        recipe.setIngredients(ingredients);
        
        return recipe;
    }
    
    private Ingredient getIngredient(String name) {
        
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        
        return ingredient;
        
    }

}
