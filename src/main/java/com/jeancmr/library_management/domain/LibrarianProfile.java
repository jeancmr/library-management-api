package com.jeancmr.library_management.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
