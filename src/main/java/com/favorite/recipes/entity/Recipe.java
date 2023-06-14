package com.favorite.recipes.entity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "recipe")
public class Recipe extends Base {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "uuid", unique = true, nullable = false, columnDefinition = "uuid")
    @GeneratedValue
    private UUID uuid;

    @Column(name = "name", columnDefinition = "varchar(250)")
    @NotBlank(message = "{recipe.name.error}")
    @Size(min = 1, max = 255, message = "{recipe.name.size.error}")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(250)")
    private String description;

    @Column(name = "instruction", columnDefinition = "TEXT")
    private String instruction;

    @Column(name = "servings", columnDefinition = "integer")
    private Integer servings;

    @Column(name = "is_vegeterian", columnDefinition = "varchar(250) default 'Yes'")
    private String isVegeterian;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "recipe_ingredient", joinColumns = { @JoinColumn(name = "recipe_id") }, inverseJoinColumns = {
            @JoinColumn(name = "ingredient_name") })
    private Set<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {

        this.ingredients.add(ingredient);
        ingredient.getRecipes().add(this);

    }

    public void removeIngredient(String ingredient) {
        
        Optional<Ingredient> findIngredient = Optional.ofNullable(this.ingredients.stream().filter(i -> i.getName().equals(ingredient)).findFirst())
        .orElse(null);

        /*Ingredient findIngredient = this.ingredients.stream().filter(i -> i.getName().equals(ingredient)).findFirst()
                .orElse(null);*/
        if (findIngredient.isPresent()) {

           this.ingredients.remove(findIngredient.get());
           findIngredient.get().getRecipes().remove(this);

        }
    }

}
