package com.kpi.recipes.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Recipe {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "userId", nullable = false)
        private User user;
        @Column(name = "title", length = 255, nullable = false)
        private String title;
        @Column(name = "description", length = 255, nullable = false)
        private String description;
        @Column(name = "time", nullable = false)
        private Long time;
        @Column(name = "calorie", nullable = false)
        private Long calorie;
        @ManyToOne()
        @JoinColumn(name = "CategoryId")
        private Category category;


        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
        private List<Ingredient> ingredients =  new ArrayList<>();

        @ManyToOne
        @JoinColumn(name = "menuRecipeId", insertable = false, updatable = false)
        private MenuRecipe menuRecipe;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_image", referencedColumnName = "id")
    private Image image;


    public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getCalorie() {
        return calorie;
    }

    public void setCalorie(Long calorie) {
        this.calorie = calorie;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ingredient> getRecipeIngredients() {
        return ingredients;
    }

    public void setRecipeIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MenuRecipe getMenuRecipe() {
        return menuRecipe;
    }

    public void setMenuRecipe(MenuRecipe menuRecipe) {
        this.menuRecipe = menuRecipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
