package com.jeancmr.library_management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeancmr.library_management.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column()
    private String secondName;

    @Column(nullable = false)
    private String firstSurname;

    @Column()
    private String secondSurname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private MemberProfile memberProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private LibrarianProfile librarianProfile;

    public void assignMemberProfile(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;

        if (memberProfile != null) {
            memberProfile.setUser(this);
        }
    }

    public void assignLibrarianProfile(LibrarianProfile librarianProfile) {
        this.librarianProfile = librarianProfile;

        if (librarianProfile != null) {
            librarianProfile.setUser(this);
        }
    }
}
