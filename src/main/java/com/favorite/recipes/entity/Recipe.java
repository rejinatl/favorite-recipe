package com.favorite.recipes.entity;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
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
@NamedEntityGraph(name = "Recipe.ingredients",attributeNodes = {
        @NamedAttributeNode("ingredients")
})
public class Recipe extends Base {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @Schema(hidden = true)
    private String id;
    
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

}
