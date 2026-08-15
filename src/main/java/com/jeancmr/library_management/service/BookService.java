package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Author;
import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.domain.Category;
import com.jeancmr.library_management.domain.Publisher;
import com.jeancmr.library_management.dto.Book.BookRequestDto;
import com.jeancmr.library_management.exception.ResourceAlreadyExistsException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.BookMapper;
import com.jeancmr.library_management.repository.AuthorRepository;
import com.jeancmr.library_management.repository.BookRepository;
import com.jeancmr.library_management.repository.CategoryRepository;
import com.jeancmr.library_management.service.interfaces.IAuthorService;
import com.jeancmr.library_management.service.interfaces.IBookService;
import com.jeancmr.library_management.service.interfaces.ICategoryService;
import com.jeancmr.library_management.service.interfaces.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final BookRepository    bookRepository;
    private final BookMapper        bookMapper;
    private final IPublisherService publisherService;
    private final IAuthorService    authorService;
    private final ICategoryService  categoryService;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Book", "ID", id));
    }

    @Override
    @Transactional
    public Book save(BookRequestDto dto) {
        if(bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new ResourceAlreadyExistsException("Book", "ISBN", dto.getIsbn());
        }

        Book newBook = bookMapper.toEntity(dto);

        Publisher publisher = publisherService.findEntityById(dto.getPublisherId());

        Set<Author> authors = dto.getAuthorsId().stream().map(authorService::findEntityById)
                .collect(Collectors.toSet());

        Set<Category> categories = dto.getCategoriesId().stream().map(categoryService::findEntityById)
                .collect(Collectors.toSet());

        newBook.setPublisher(publisher);
        authors.forEach(newBook::addAuthor);
        categories.forEach(newBook::addCategory);

        return bookRepository.save(newBook);
    }
}
