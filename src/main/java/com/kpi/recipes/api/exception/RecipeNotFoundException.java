package com.kpi.recipes.api.exception;

public class RecipeNotFoundException extends Exception{

    public RecipeNotFoundException(String message){
        super(message);
    }
    public RecipeNotFoundException(){}
}
