package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMemberService {
    List<MemberProfile> findAll();
    MemberProfile findById(Long id);
    MemberProfile save(UserCreateRequestDto userCreateRequestDto);
    MemberProfile update(Long id, UserUpdateRequestDto userUpdateRequestDto);
    void deleteById(Long id);
}
