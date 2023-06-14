package com.favorite.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.favorite.recipes.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String> {

}
