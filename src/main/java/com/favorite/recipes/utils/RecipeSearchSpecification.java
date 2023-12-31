package com.favorite.recipes.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.favorite.recipes.entity.Ingredient;
import com.favorite.recipes.entity.Recipe;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Component
public class RecipeSearchSpecification {

    public Specification<Recipe> searchRecipe(String isVegetarian, Integer servings, String includeIngredient,
            String excludeIngredient, String instructionContains) {
        
        return (root, query, criteriaBuilder) -> {

            query.orderBy(criteriaBuilder.asc(root.get("name")));
            
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(isVegetarian)) {

                predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(root.get("isVegetarian")), isVegetarian.toUpperCase()));
            }

            if (servings >= 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("servings"), servings));

            }
            if (StringUtils.isNotBlank(instructionContains)) {

                predicates.add(criteriaBuilder.like( criteriaBuilder.upper(root.get("instruction")), "%" + instructionContains.toUpperCase() + "%"));
            }

            if (StringUtils.isNotBlank(includeIngredient)) {
                
                Join<Recipe, Ingredient> join = root.join("ingredients", JoinType.INNER);
                List<String> includeIngredientList = new ArrayList<>();
                includeIngredientList.add(includeIngredient);
                predicates.add(join.get("name").in(includeIngredientList));

            }
            if (StringUtils.isNotBlank(excludeIngredient)) {

                Subquery<Ingredient> subquery = query.subquery(Ingredient.class);
                Root<Recipe> subqueryRoot = subquery.correlate(root);
                Join<Recipe, Ingredient> ingredientJoin = subqueryRoot.join("ingredients");
                subquery.select(ingredientJoin)
                        .where(criteriaBuilder.equal( criteriaBuilder.upper(ingredientJoin.get("name")), excludeIngredient.toUpperCase()));

                predicates.add(criteriaBuilder.not(criteriaBuilder.exists(subquery)));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }

}
