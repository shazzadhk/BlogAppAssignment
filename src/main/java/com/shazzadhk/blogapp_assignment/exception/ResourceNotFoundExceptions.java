package com.shazzadhk.blogapp_assignment.exception;

import lombok.Data;

@Data
public class ResourceNotFoundExceptions extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundExceptions(String resourceName,String fieldName,long fieldValue){
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
