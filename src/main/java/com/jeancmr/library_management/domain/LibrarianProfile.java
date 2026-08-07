package com.jeancmr.library_management.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "librarians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianProfile {
    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDate hiredDate;

    @OneToMany(mappedBy = "librarian")
    private Set<Loan> loans = new HashSet<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
