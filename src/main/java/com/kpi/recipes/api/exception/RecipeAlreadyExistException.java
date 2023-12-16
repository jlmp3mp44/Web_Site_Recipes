package com.kpi.recipes.api.exception;

public class RecipeAlreadyExistException extends Exception{

    public RecipeAlreadyExistException(String message) {
        super(message);
    }
    public RecipeAlreadyExistException(){}
}
