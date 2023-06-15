package com.favorite.recipes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

@Tag(name = "${recipe.title}", description = "${recipe.title.description}")
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
    
    /** Retrieve recipes based on given search criteria */
    @Operation(summary = "${recipe.controller.search.recipe.title}", description = "${recipe.controller.search.recipe.description}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "${recipe.controller.successful.operation}", content = {
                @Content(schema = @Schema(implementation = Recipe.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "204", description = "${recipe.controller.no.data.found}", content = {
                @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", description = "${recipe.controller.application.error}", content = {
                @Content(schema = @Schema()) }) })
    
     @GetMapping("/recipe")
     public ResponseEntity<List<Recipe>> getRecipe(
             @Schema(description = "${recipe.controller.search.recipe.isveg}")
             @RequestParam(required = false) String isVegetarian,
             @Schema(description = "${recipe.controller.search.recipe.no.serv}", type = "Interger")
             @RequestParam(required = false, defaultValue = "0") Integer servings,
             @Schema(description = "${recipe.controller.search.recipe.contains.ingredient}")
             @RequestParam(required = false) String includeIngredient,
             @Schema(description = "${recipe.controller.search.recipe.not.contains.ingredient}")
             @RequestParam(required = false) String excludeIngredient,
             @Schema(description = "${recipe.controller.search.recipe.contains.instruction}")
             @RequestParam(required = false) String instructionContains
             );
    
    
    /** Update recipe */
    @Operation(summary = "${recipe.controller.update.recipe}", description = "${recipe.controller.update.recipe.desc}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "${recipe.controller.successful.operation}", content = {
                    @Content(schema = @Schema(implementation = Recipe.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "${recipe.controller.mandatory.fields}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "${recipe.controller.unknown.data.error}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "${recipe.controller.application.error}", content = {
                    @Content(schema = @Schema()) }) })
    @PutMapping("/recipe/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") String id, @RequestBody @Valid Recipe recipe);
    
    
    /** Delete a Recipe by specifying its ID. **/
    @Operation(summary = "${recipe.controller.delete.recipe}", description = "${recipe.controller.delete.recipe.desc}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "${recipe.controller.successful.operation}", content = {
                @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "204", description = "${recipe.controller.no.data.found}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "${recipe.controller.unknown.data.error}", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "${Application error}", content = {
                    @Content(schema = @Schema()) }) })
    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") String id);

}
