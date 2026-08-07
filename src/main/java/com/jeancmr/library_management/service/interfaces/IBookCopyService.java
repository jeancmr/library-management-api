package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.dto.BookCopyRequestDto;
import com.jeancmr.library_management.dto.BookCopyStatusRequestDto;

import java.util.List;

public interface IBookCopyService {
    List<BookCopy> findAll();
    BookCopy findById(Long id);
    List<BookCopy> findByBookId(Long bookId);
    BookCopy create(BookCopyRequestDto requestDto);
    void delete(Long id);
    BookCopy updateStatus(Long id, BookCopyStatusRequestDto updateStatusDto);
}
