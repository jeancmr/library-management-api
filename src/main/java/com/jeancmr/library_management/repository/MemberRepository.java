package com.jeancmr.library_management.repository;

import com.jeancmr.library_management.domain.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberProfile,Long> {
//    List<MemberResponse> getAll();
}
