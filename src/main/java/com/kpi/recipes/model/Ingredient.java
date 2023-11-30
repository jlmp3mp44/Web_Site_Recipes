package com.kpi.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "Ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private Long price;
    @JsonIgnore
    @OneToOne(mappedBy = "ingredient", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private IngredientRecipe ingredientRecipe =  new IngredientRecipe();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public IngredientRecipe getRecipeIngredient() {
        return ingredientRecipe;
    }

    public void setRecipeIngredient(IngredientRecipe ingredientRecipe) {
        this.ingredientRecipe = ingredientRecipe;
    }
}
