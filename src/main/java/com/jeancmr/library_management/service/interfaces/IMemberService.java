package com.jeancmr.library_management.service.interfaces;

import com.jeancmr.library_management.dto.MemberResponseDto;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMemberService {
    List<MemberResponseDto> findAll();
    MemberResponseDto findById(Long id);
    MemberResponseDto save(UserCreateRequestDto userCreateRequestDto);
    MemberResponseDto update(Long id, UserUpdateRequestDto userUpdateRequestDto);
    void deleteById(Long id);
}
