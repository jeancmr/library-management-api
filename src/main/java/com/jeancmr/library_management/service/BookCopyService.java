package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.domain.BookCopy;
import com.jeancmr.library_management.dto.BookCopy.BookCopyRequestDto;
import com.jeancmr.library_management.dto.BookCopy.BookCopyStatusRequestDto;
import com.jeancmr.library_management.enums.BookCopyStatus;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
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

    @Override
    @Transactional(readOnly = true)
    public List<BookCopy> findAll() {
        return bookCopyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCopy> findByBookId(Long bookId) {
        if(!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book", "ID",  bookId);
        }
        return bookCopyRepository.findByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public BookCopy findById(Long id) {
        return bookCopyRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("BookCopy", "ID", id));
    }

    @Override
    @Transactional
    public BookCopy create(BookCopyRequestDto requestDto) {
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", requestDto.getBookId()));

        BookCopy bookCopy = new BookCopy();
        bookCopy.setBook(book);
        bookCopy.setStatus(requestDto.getStatus() != null
                ? requestDto.getStatus()
                : BookCopyStatus.AVAILABLE);

        return bookCopyRepository.save(bookCopy);
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
    public BookCopy updateStatus(Long id, BookCopyStatusRequestDto updateStatusDto) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookCopy", "ID", id));

        bookCopy.setStatus(updateStatusDto.getStatus());

        return bookCopyRepository.save(bookCopy);
    }
}
