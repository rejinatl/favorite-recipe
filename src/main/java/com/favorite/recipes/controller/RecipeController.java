package com.favorite.recipes.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.favorite.recipes.entity.Recipe;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Recipe", description = "Recipe Management API")
@PropertySource("classpath:recipe-controller-api-doc.properties")
public interface RecipeController {
    
    /** Create a Recipe by specifying its name,description,instruction etc. **/
    @Operation(summary = "${recipe.controller.create.recipe.title}", description = "${recipe.controller.create.recipe.description}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "${recipe.controller.successful.operation}", content = {
                    @Content(schema = @Schema(implementation = Recipe.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "${recipe.controller.manatory.field.error}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "${recipe.controller.unknown.data.error}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "${recipe.controller.application.error}", content = {
                    @Content(schema = @Schema()) }) })
    
    @PostMapping("/recipe")
    public ResponseEntity<Recipe> createRecipe(@RequestBody @Valid Recipe recipe);

}
