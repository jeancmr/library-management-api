package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.BookCopy;

import java.util.List;

public interface IBookCopyService {
    List<BookCopy> findAll();
    BookCopy findById(Long id);
    BookCopy save(BookCopy bookCopy);
}
