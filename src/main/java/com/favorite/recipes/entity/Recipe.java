package com.favorite.recipes.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
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
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@Table(name = "recipe")
public class Recipe extends Base {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @Schema(hidden = true)
    private String id;
    
    //@Id
    @Column(name = "name")
    @NotBlank(message = "{recipe.name.notempty}")
    @Size(min = 1, max = 255, message = "{recipe.name.size.error}")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "instruction")
    private String instruction;

    @Column(name = "servings")
    private Integer servings;

    @Column(name = "is_vegetarian")
    private String isVegetarian ;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "recipe_ingredient", joinColumns = { @JoinColumn(name = "recipe_id") }, inverseJoinColumns = {
            @JoinColumn(name = "ingredient_name") })
    @OrderBy("UPPER(name) ASC")
    private Set<Ingredient> ingredients = new HashSet<>();

    public void addIngredient(Ingredient ingredient) {

        this.ingredients.add(ingredient);
        ingredient.getRecipes().add(this);

    }

    public void removeIngredient(String ingredient) {
        
        Optional<Ingredient> findIngredient = Optional.ofNullable(this.ingredients.stream().filter(i -> i.getName().equals(ingredient)).findFirst())
        .orElse(null);

        if (findIngredient.isPresent()) {

           this.ingredients.remove(findIngredient.get());
           findIngredient.get().getRecipes().remove(this);

        }
    }

}
