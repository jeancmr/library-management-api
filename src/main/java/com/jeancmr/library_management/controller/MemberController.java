package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.dto.MemberResponseDto;
import com.jeancmr.library_management.mapper.MemberProfileMapper;
import com.jeancmr.library_management.mapper.UserMapper;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.service.interfaces.IMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final IMemberService memberService;
    private final MemberProfileMapper  memberProfileMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public List<MemberResponseDto> findAll() {
        return memberService.findAll().stream()
                .map(memberProfileMapper::toResponseDto).toList();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<MemberResponseDto> save(@Valid @RequestBody UserCreateRequestDto requestDto) {
        MemberProfile savedMember = memberService.save(requestDto);
        MemberResponseDto responseDto = memberProfileMapper.toResponseDto(savedMember);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<MemberResponseDto> update(@PathVariable Long id,
                                                     @RequestBody UserUpdateRequestDto requestDto) {
        MemberProfile updatedMember = memberService.update(id, requestDto);
        MemberResponseDto responseDto = memberProfileMapper.toResponseDto(updatedMember);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {
        MemberProfile foundMember =  memberService.findById(id);
        MemberResponseDto responseDto =  memberProfileMapper.toResponseDto(foundMember);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
