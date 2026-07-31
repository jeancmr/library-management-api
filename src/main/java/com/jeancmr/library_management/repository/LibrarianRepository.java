package com.jeancmr.library_management.repository;

import com.jeancmr.library_management.domain.LibrarianProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<LibrarianProfile,Long> {
}
