package com.favorite.recipes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.favorite.recipes.controller.impl.RecipeControllerImpl;
import com.favorite.recipes.entity.Ingredient;
import com.favorite.recipes.entity.Recipe;
import com.favorite.recipes.service.RecipeService;

@WebMvcTest(controllers = RecipeControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RecipeControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RecipeService recipeService;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @DisplayName("Create a Recipe")
    @Test
    public void createRecipe_ReturnCreated() throws Exception {
        
        Recipe recipe = getDummyRecipe();
        
        when(recipeService.createRecipe(any(Recipe.class))).thenReturn(recipe);
        ResultActions response = mockMvc.perform(
                post("/recipe").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(recipe)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
        
    }
    
    @DisplayName("Update recipe")
    @Test
    public void update_Recipe_and_Return_OK() throws Exception {
        
        Recipe recipe = getDummyRecipe();
        
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Chicken fry");
        newRecipe.setDescription("Chicken fry");
        
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient in1 = getIngredient("Chicken");
        Ingredient in2 = getIngredient("Oil");
        ingredients.addAll(Arrays.asList(in1,in2));
        newRecipe.setIngredients(ingredients);
        
        when(recipeService.updateRecipe(anyString(), any(Recipe.class))).thenReturn(newRecipe);
        ResultActions response = mockMvc.perform(put("/recipe/{id}", "28c004e2-f2e7-4a48-90a9-cad60255fcad")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe)));
        
        response.andExpect(status().isCreated()).andExpect(jsonPath("$.name").value(newRecipe.getName()))
        .andExpect(jsonPath("$.description").value(newRecipe.getDescription()));
        
    }
    
    @DisplayName("Delete a recipe return_OK")
    @Test
    public void deleteRecipe_Return_OK() throws Exception {
        
        when(recipeService.deleteRecipe(any())).thenReturn(true);

        ResultActions response = mockMvc.perform(delete("/recipe/{id}", "28c004e2-f2e7-4a48-90a9-cad60255fcad"));

        response.andExpect(status().isOk());

    }
    
    @DisplayName("Delete a recipe return_NO_CONTENT")
    @Test
    public void deleteRecipe_Return_NO_CONTENT() throws Exception {
        
        when(recipeService.deleteRecipe(any())).thenReturn(false);

        ResultActions response = mockMvc.perform(delete("/recipe/{id}", "28c004e2-f2e7-4a48-90a9-cad60255fcad"));
        response.andExpect(status().isNoContent());

    }
    
    @DisplayName("Get recipes")
    @Test
    public void getRecipes_Return_OK() throws Exception {
        
        List<Recipe> recipes = new ArrayList<>();
        
        Recipe recipe1 = getDummyRecipe();
        Recipe recipe2 = getDummyRecipe2();
        recipes.addAll(Arrays.asList(recipe1,recipe2));
        
        when(recipeService.getRecipe(anyString(),anyInt(),anyString(),anyString(),anyString())).thenReturn(recipes);
        ResultActions response = mockMvc.perform(get("/recipe")
                .param("isVegetarian", "Yes")
                .param("servings", "10")
                .param("includeIngredient", "chicken")
                .param("excludeIngredient", "oil")
                .param("instructionContains", ""));
        
        response.andExpect(status().isOk()).andDo(print());
        
    }
    
    @DisplayName("get recipe by id")
    @Test
    public void getRecipeById_Return_OK() throws Exception {
        
        Recipe recipe = getDummyRecipe();
        when(recipeService.getRecipeById(anyString())).thenReturn(Optional.of(recipe));
        ResultActions response = mockMvc.perform(
                get("/recipe/{id}","28c004e2-f2e7-4a48-90a9-cad60255fcad"));
        response.andExpect(status().isOk()).andDo(print());
    }
    
    @DisplayName("get recipe by id return empty")
    @Test
    public void getRecipeById_Return_Empty() throws Exception {
        
        when(recipeService.getRecipeById(anyString())).thenReturn(Optional.empty());
        ResultActions response = mockMvc.perform(
                get("/recipe/{id}","100"));
        response.andExpect(status().isNoContent()).andDo(print());
    }
    
    private Recipe getDummyRecipe() {
        
        Recipe recipe =new Recipe();
        Set<Ingredient> ingredients = new HashSet<>();
        
        Ingredient in1 = getIngredient("Chicken");
        Ingredient in2 = getIngredient("Oil");
        Ingredient in3 = getIngredient("Chilli");
        Ingredient in4 = getIngredient("Potatoes");
        ingredients.addAll(Arrays.asList(in1,in2,in3,in4));
        
        recipe.setId("28c004e2-f2e7-4a48-90a9-cad60255fcad");
        recipe.setName("chicken curry");
        recipe.setDescription("description");
        recipe.setInstruction("instruction");
        recipe.setIsVegetarian("No");
        recipe.setServings(100);
        recipe.setIngredients(ingredients);
        
        return recipe;
    }
    
 private Recipe getDummyRecipe2() {
        
        Recipe recipe =new Recipe();
        Set<Ingredient> ingredients = new HashSet<>();
        
        Ingredient in1 = getIngredient("Paneer");
        Ingredient in2 = getIngredient("Oil");
        Ingredient in3 = getIngredient("Chilli");
        Ingredient in4 = getIngredient("Potatoes");
        ingredients.addAll(Arrays.asList(in1,in2,in3,in4));
        
        recipe.setId("28c004e2-f2e7-4a48-90a9-cad60255fddc");
        recipe.setName("Paneer");
        recipe.setDescription("Paneer curry");
        recipe.setInstruction("Paneer curry");
        recipe.setServings(10);
        recipe.setIsVegetarian("Yes");
        recipe.setIngredients(ingredients);
        
        return recipe;
    }
    
    private Ingredient getIngredient(String name) {
        
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        
        return ingredient;
        
    }
}
