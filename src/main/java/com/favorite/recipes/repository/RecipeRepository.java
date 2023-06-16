package com.favorite.recipes.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.favorite.recipes.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String>, JpaSpecificationExecutor<Recipe> {
    
    @EntityGraph(type=EntityGraphType.FETCH, value="Recipe.ingredients")
    public List<Recipe> findAll(Specification<Recipe> recipeSpec);
}
