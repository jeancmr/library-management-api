package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.dto.BookCopyRequestDto;
import com.jeancmr.library_management.dto.BookCopyResponseDto;
import com.jeancmr.library_management.dto.BookCopyStatusRequestDto;
import com.jeancmr.library_management.enums.BookCopyStatus;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.BookMapper;
import com.jeancmr.library_management.repository.BookCopyRepository;
import com.jeancmr.library_management.repository.BookRepository;
import com.jeancmr.library_management.service.interfaces.IBookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCopyService implements IBookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository      bookRepository;
    private final BookMapper           bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookCopyResponseDto> findAll() {
        return bookCopyRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCopyResponseDto> findByBookId(Long bookId) {
        if(!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book", "ID",  bookId);
        }

        return bookCopyRepository.findByBookId(bookId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public BookCopyResponseDto create(BookCopyRequestDto requestDto) {
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", requestDto.getBookId()));

        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(book);
        bookCopy.setStatus(requestDto.getStatus() != null
                ? requestDto.getStatus()
                : BookCopyStatus.AVAILABLE);

        return toResponseDto(bookCopyRepository.save(bookCopy));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookCopy", "ID", id));

        bookCopyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BookCopyResponseDto updateStatus(Long id, BookCopyStatusRequestDto updateStatusDto) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookCopy", "ID", id));

        bookCopy.setStatus(updateStatusDto.getStatus());

        return toResponseDto(bookCopyRepository.save(bookCopy));
    }

    private BookCopyResponseDto toResponseDto(BookCopy bookCopy) {
        BookCopyResponseDto responseDto = new BookCopyResponseDto();
        responseDto.setId(bookCopy.getId());
        responseDto.setStatus(bookCopy.getStatus());
        responseDto.setBook(bookMapper.toResponseDto(bookCopy.getBook()));

        return responseDto;
    }
}
