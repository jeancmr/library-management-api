package com.jeancmr.library_management.exception;

public class BookCopyNotAvailableException extends RuntimeException {
    public BookCopyNotAvailableException(Long bookCopyId) {
        super("Book copy with id " + bookCopyId + " is not available");
    }
}
