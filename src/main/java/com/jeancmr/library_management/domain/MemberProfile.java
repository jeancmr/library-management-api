package com.jeancmr.library_management.domain;

import com.jeancmr.library_management.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfile {
    @Id
    private Long id;

    @Column(nullable = false)
    private LocalDate membershipDate;

    @Column(nullable = false)
    private int borrowLimit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToOne(fetch =  FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "member")
    private Set<Loan> loans = new HashSet<>();
}
