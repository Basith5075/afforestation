package com.plant.exception;

public class EntityNotFoundException extends RuntimeException {

    String message;

    public EntityNotFoundException (String message){
        super(message);
    }
}