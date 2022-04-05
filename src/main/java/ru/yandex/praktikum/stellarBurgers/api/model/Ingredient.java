package ru.yandex.praktikum.stellarBurgers.api.model;

public class Ingredient {
    private String[] ingredients;

    public Ingredient(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
