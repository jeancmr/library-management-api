package com.jeancmr.library_management.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> resource, Long id) {
        super(resource.getSimpleName() + " not found with id " + id);
    }
}
