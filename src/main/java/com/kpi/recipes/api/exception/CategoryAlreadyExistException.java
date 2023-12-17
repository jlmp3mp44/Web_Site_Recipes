package com.kpi.recipes.api.exception;

public class CategoryAlreadyExistException extends Exception{
    public CategoryAlreadyExistException(String message){
        super(message);
    }
    public CategoryAlreadyExistException(){}
}
