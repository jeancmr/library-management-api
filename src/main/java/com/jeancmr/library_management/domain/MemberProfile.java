package com.jeancmr.library_management.domain;

import com.jeancmr.library_management.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
}
