package com.jeancmr.library_management.repository;

import com.jeancmr.library_management.domain.LibrarianProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<LibrarianProfile,Long> {
}
