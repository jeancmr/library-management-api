package com.jeancmr.library_management.data;

import com.jeancmr.library_management.domain.*;
import com.jeancmr.library_management.enums.MemberStatus;
import com.jeancmr.library_management.enums.Role;
import com.jeancmr.library_management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if(!userRepository.existsByEmail("admin@example.com")){
            User admin = new User();
            admin.setFirstName("Eduardo");
            admin.setSecondName("Edgar");
            admin.setFirstSurname("Manrique");
            admin.setSecondSurname("Segoviano");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin1234"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN));
            admin.setBirthDate(LocalDate.of(2000, 5,15));

            userRepository.save(admin);
            System.out.println("user 'ADMIN' created.");

        }

        if(!userRepository.existsByEmail("edgar@member.com")){
            MemberProfile memberProfile = new MemberProfile();
            memberProfile.setStatus(MemberStatus.ACTIVE);
            memberProfile.setMembershipDate(LocalDate.now());
            memberProfile.setBorrowLimit(4);

            User userMember = new User();
            userMember.setFirstName("Paolo");
            userMember.setSecondName("Testing");
            userMember.setFirstSurname("Test");
            userMember.setSecondSurname("Testeando");
            userMember.setEmail("edgar@member.com");
            userMember.setPassword(passwordEncoder.encode("paolo123"));
            userMember.setRoles(Set.of(Role.ROLE_MEMBER));
            userMember.setBirthDate(LocalDate.of(2002, 2,10));
            userMember.assignMemberProfile(memberProfile);

            userRepository.save(userMember);
            System.out.println("user 'MEMBER' created.");
        }

        if(!userRepository.existsByEmail("maria@librarian.com")){
            LibrarianProfile librarianProfile = new LibrarianProfile();
            librarianProfile.setHiredDate(LocalDate.now());

            User userLibrarian = new User();
            userLibrarian.setFirstName("María");
            userLibrarian.setSecondName("");
            userLibrarian.setFirstSurname("Nolan");
            userLibrarian.setSecondSurname("");
            userLibrarian.setEmail("maria@librarian.com");
            userLibrarian.setPassword(passwordEncoder.encode("maria123"));
            userLibrarian.setRoles(Set.of(Role.ROLE_LIBRARIAN));
            userLibrarian.setBirthDate(LocalDate.of(1996, 1,22));
            userLibrarian.assignLibrarianProfile(librarianProfile);

            userRepository.save(userLibrarian);
            System.out.println("user 'LIBRARIAN' created.");
        }

        // --- CATEGORIES ---

        Category magicalRealism = new Category();
        magicalRealism.setName("Magical Realism");
        magicalRealism.setDescription("magic realism books");
        Category category1 = categoryRepository.save(magicalRealism);
        System.out.println("category 1 created.");

        Category fiction = new Category();
        fiction.setName("Fiction");
        fiction.setDescription("Fiction books");
        Category category2 = categoryRepository.save(fiction);
        System.out.println("category 2 created.");

        // --- PUBLISHER ---
        Publisher publisher  = new Publisher(null, "Harper Perennial", new HashSet<>());
        Publisher savedPublisher = publisherRepository.save(publisher);
        System.out.println("publisher created.");

        // --- AUTHOR ---
        Author author  = new Author(null, "Gabriel García Márquez",
                LocalDate.of(1927,3,6),
                "Colombia",
                "Was a famous Colombian writer, journalist, and Nobel Prize " +
                        "laureate widely considered one of the greatest authors" +
                        " of the 20th century.",
                new HashSet<>());
        Author savedAuthor = authorRepository.save(author);
        System.out.println("Author " + author.getName() + " created.");

        // --- BOOKS ---
        Book book1 = new Book();
        book1.setIsbn("8482806866");
        book1.setTitle("100 hundred years of solitude");
        book1.setPublicationDate(LocalDate.of(1967,5,30));
        book1.setPublisher(savedPublisher);
        book1.setCategories(Set.of(category1, category2));
        book1.setAuthors(Set.of(savedAuthor));

        bookRepository.save(book1);
        System.out.println("Book " + book1.getTitle() + " created.");
    }
}