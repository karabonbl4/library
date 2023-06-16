package com.library.exception;

import java.util.List;

public class MissingRequiredDataException extends RuntimeException{
    
    public MissingRequiredDataException(){
        super("You have not entered the required data!");
    }

    public MissingRequiredDataException(List<String> missingType){
        super(String.format("Not entered data: %s", missingType));
    }
}
