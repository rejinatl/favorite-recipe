package com.favorite.recipes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.favorite.recipes.entity.Recipe;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Recipe", description = "Recipe Management API")
public interface RecipeController {
    
    /** Create a Recipe by specifying its name,description,instruction etc. **/
    @Operation(summary = "${recipe.controller.create.recipe.title}", description = "${recipe.controller.create.recipe.description}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "${recipe.controller.successful.operation}", content = {
                    @Content(schema = @Schema(implementation = Recipe.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "${recipe.controller.mandatory.field.error}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "${recipe.controller.unknown.data.error}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", description = "${recipe.controller.recipe.name.exists}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "${recipe.controller.application.error}", content = {
                    @Content(schema = @Schema()) }) })
    
    @PostMapping("/recipe")
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid Recipe recipe);
    
    /** Retrive Recipe */
    @Operation(summary = "${recipe.controller.search.recipe.title}", description = "${recipe.controller.search.recipe.description}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                @Content(schema = @Schema(implementation = Recipe.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "204", description = "No data found", content = {
                @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", description = "Application error", content = {
                @Content(schema = @Schema()) }) })
    
     @GetMapping("/recipe")
     public ResponseEntity<List<Recipe>> getRecipe(
             @Schema(description = "Yes/No")
             @RequestParam(required = false) String isVegetarian,
             @Schema(description = "Number of servings", type = "Interger")
             @RequestParam(required = false, defaultValue = "0") Integer servings,
             @Schema(description = "Search for recipe which contains the ingredient")
             @RequestParam(required = false) String includeIngredient,
             @Schema(description = "Search for recipe which not contain the ingredient")
             @RequestParam(required = false) String excludeIngredient,
             @Schema(description = "Search for recipe which contain the instruction")
             @RequestParam(required = false) String instructionContains
             );

}
