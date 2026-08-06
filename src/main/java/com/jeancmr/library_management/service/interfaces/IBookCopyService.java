package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.dto.BookCopyRequestDto;
import com.jeancmr.library_management.dto.BookCopyResponseDto;
import com.jeancmr.library_management.dto.BookCopyStatusRequestDto;
import com.jeancmr.library_management.enums.BookCopyStatus;

import java.util.List;

public interface IBookCopyService {
    List<BookCopyResponseDto> findAll();
    List<BookCopyResponseDto> findByBookId(Long bookId);
    BookCopyResponseDto create(BookCopyRequestDto requestDto);
    void delete(Long id);
    BookCopyResponseDto updateStatus(Long id, BookCopyStatusRequestDto updateStatusDto);
}
