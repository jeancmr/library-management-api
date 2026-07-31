package com.jeancmr.library_management.service;

import com.jeancmr.library_management.dto.MemberCreateRequestDto;
import com.jeancmr.library_management.dto.MemberResponseDto;
import com.jeancmr.library_management.dto.MemberUpdateRequestDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMemberService {
    List<MemberResponseDto> findAll();
    MemberResponseDto findById(Long id);
    MemberResponseDto save(MemberCreateRequestDto memberCreateRequestDto);
    MemberResponseDto update(Long id, MemberUpdateRequestDto memberUpdateRequestDto);
    void deleteById(Long id);
}
