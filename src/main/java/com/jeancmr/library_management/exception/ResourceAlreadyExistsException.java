package com.jeancmr.library_management.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

  public ResourceAlreadyExistsException(String resource, String field, Object value) {
    super("%s with %s '%s' already exists".formatted(resource, field, value));
  }
}