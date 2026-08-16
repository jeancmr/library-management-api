package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.Author;
import com.jeancmr.library_management.domain.Book;
import com.jeancmr.library_management.domain.Category;
import com.jeancmr.library_management.domain.Publisher;
import com.jeancmr.library_management.dto.Book.BookRequestDto;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.BookMapper;
import com.jeancmr.library_management.repository.BookRepository;
import com.jeancmr.library_management.service.interfaces.IAuthorService;
import com.jeancmr.library_management.service.interfaces.ICategoryService;
import com.jeancmr.library_management.service.interfaces.IPublisherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private IPublisherService publisherService;

    @Mock
    private IAuthorService authorService;

    @Mock
    private ICategoryService categoryService;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Should return all books successfully")
    void shouldReturnAllBooksSuccessfully() {
        List<Book> bookList = List.of(new Book(), new Book(), new Book());

        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result =  bookService.findAll();

        assertSame(bookList, result);
        assertEquals(bookList.size(), result.size());

        verify(bookRepository).findAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Should return empty list when there are no books")
    void  shouldReturnEmptyListWhenThereAreNoBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        List<Book> result  =  bookService.findAll();

        assertTrue(result.isEmpty());

        verify(bookRepository).findAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Should return a Book by ID when book exists")
    void shouldReturnBookByIdWhenBookExists() {
        Long id = 10L;
        Book book = new Book();
        book.setId(id);
        book.setIsbn("8482806866");
        book.setTitle("100 hundred years of solitude");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Book  result = bookService.findById(id);

        assertSame(book, result);
        verify(bookRepository).findById(id);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Should throw exception when book does not exist")
    void  shouldThrowExceptionWhenBookDoesNotExist() {
        Long id = 10L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> bookService.findById(id));

        assertEquals("Book with ID '" + id + "' not found", exception.getMessage());

        verify(bookRepository).findById(id);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Should save successfully book when ISBN is not used")
    void shouldSaveBookWhenIsbnIsNotUsed() {
        Category category = new Category(10L, "Magical Realism",
                "magic realism books", new HashSet<>());
        Publisher publisher  = new Publisher(10L, "Harper Perennial", new HashSet<>());
        Author author  = new Author(20L, "Gabriel García Márquez",
                LocalDate.of(1927,3,6),
                "Colombia",
                "Was a famous Colombian writer, journalist, and Nobel Prize " +
                        "laureate widely considered one of the greatest authors" +
                        " of the 20th century.",
                new HashSet<>());

        BookRequestDto bookToSaveDto = new BookRequestDto();
        bookToSaveDto.setIsbn("8482806866");
        bookToSaveDto.setTitle("100 hundred years of solitude");
        bookToSaveDto.setPublicationDate(LocalDate.of(1967,5,30));
        bookToSaveDto.setAuthorsId(Set.of(author.getId()));
        bookToSaveDto.setCategoriesId(Set.of(category.getId()));
        bookToSaveDto.setPublisherId(publisher.getId());

        Book bookToSave = new Book();
        bookToSave.setIsbn("8482806866");
        bookToSave.setTitle("100 hundred years of solitude");
        bookToSave.setPublicationDate(LocalDate.of(1967,5,30));

        Book savedBook = new Book();
        savedBook.setId(10L);
        savedBook.setIsbn("8482806866");
        savedBook.setTitle("100 hundred years of solitude");
        savedBook.setPublicationDate(LocalDate.of(1967,5,30));
        savedBook.setCategories(Set.of(category));
        savedBook.setAuthors(Set.of(author));
        savedBook.setPublisher(publisher);

        when(bookRepository.existsByIsbn(bookToSaveDto.getIsbn())).thenReturn(false);

        when(bookMapper.toEntity(bookToSaveDto)).thenReturn(bookToSave);

        when(publisherService.findEntityById(bookToSaveDto.getPublisherId())).thenReturn(publisher);
        when(authorService.findEntityById(author.getId())).thenReturn(author);
        when(categoryService.findEntityById(category.getId())).thenReturn(category);

        when(bookRepository.save(bookToSave)).thenReturn(savedBook);

        Book result = bookService.save(bookToSaveDto);

        assertSame(savedBook, result);

        assertSame(publisher, bookToSave.getPublisher());
        assertTrue(bookToSave.getAuthors().contains(author));
        assertTrue(author.getBooks().contains(bookToSave));
        assertTrue(bookToSave.getCategories().contains(category));
        assertTrue(category.getBooks().contains(bookToSave));

        verify(bookRepository).existsByIsbn(bookToSaveDto.getIsbn());
        verify(bookMapper).toEntity(bookToSaveDto);
        verify(publisherService).findEntityById(bookToSaveDto.getPublisherId());
        verify(authorService).findEntityById(author.getId());
        verify(categoryService).findEntityById(category.getId());
        verify(bookRepository).save(bookToSave);
        verifyNoMoreInteractions(
                bookMapper,
                publisherService,
                authorService,
                categoryService,
                bookRepository
        );
    }
}