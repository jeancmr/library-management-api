package com.jeancmr.library_management.repository;

import com.jeancmr.library_management.domain.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberProfile,Long> {
}
