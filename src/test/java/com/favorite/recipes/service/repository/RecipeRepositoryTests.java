package com.favorite.recipes.service.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.favorite.recipes.entity.Ingredient;
import com.favorite.recipes.entity.Recipe;
import com.favorite.recipes.repository.RecipeRepository;
import com.favorite.recipes.utils.RecipeSearchSpecification;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:test.properties")
@Sql(scripts = "classpath:schema-h2.sql")
public class RecipeRepositoryTests {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    TestEntityManager entityManager;
    
    @MockBean
    RecipeSearchSpecification recipeSearchSpecification;
    
    @BeforeEach
    void setUp() {

        Recipe recipe = getDummyRecipe();
        entityManager.persist(recipe);

    }
    
    @DisplayName("findAll")
    @Test
    public void findAll_With_Search_Criteria() throws Exception {
        
        String isVegetarian = "no";
        Integer servings = 10;
        String includeIngredient = "Chicken";
        String excludeIngredient = "butter";
        String instructionContains = "instruction";
        
        List<Recipe> filterResult = recipeRepository.findAll(recipeSearchSpecification.searchRecipe(isVegetarian, 
                servings, includeIngredient, 
                excludeIngredient, instructionContains));
        
        assertEquals(1, filterResult.size());
        
    }

    @DisplayName("Recipe Repository - Find recipe by ID")
    @Test
    public void findById_Return_Recipe() {

        Recipe recipe = recipeRepository.findById("28c004e2-f2e7-4a48-90a9-cad60255fcad").get();
        assertEquals("chicken curry", recipe.getName());
    }
    
    @DisplayName("Recipe Repository - findById")
    @Test
    public void findById_Return_Recipe_Ok() {

        Optional<Recipe> recipe = recipeRepository.findById("28c004e2-f2e7-4a48-90a9-cad60255fcad");
        assertThat(recipe).isNotNull();
    }

    @DisplayName("Recipe repository - deleteById")
    @Test
    public void deleteById_Return_Nothing() {

        recipeRepository.deleteById("28c004e2-f2e7-4a48-90a9-cad60255fcad");
        assertThat(recipeRepository.findById("28c004e2-f2e7-4a48-90a9-cad60255fcad")).isEmpty();
    }

    @DisplayName("Recipe repository - save a Recipe")
    @Test
    public void createRecipe_Return_Ok() {

        Recipe recipe = getDummyRecipe2();
        recipeRepository.save(recipe);
        assertThat(recipeRepository.count()).isEqualTo(2);
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
