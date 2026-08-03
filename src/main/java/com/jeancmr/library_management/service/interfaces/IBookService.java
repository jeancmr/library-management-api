package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.dto.BookRequestDto;

import java.util.List;

public interface IBookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(BookRequestDto bookRequestDto);
}
