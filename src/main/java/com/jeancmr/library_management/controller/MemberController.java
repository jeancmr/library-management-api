package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.dto.MemberCreateRequestDto;
import com.jeancmr.library_management.dto.MemberResponseDto;
import com.jeancmr.library_management.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.service.IMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final IMemberService  memberService;

    @GetMapping
    public List<MemberResponseDto> findAll() {
        return memberService.findAll();
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> save(@Valid @RequestBody MemberCreateRequestDto requestDto) {
        return new ResponseEntity<>(memberService.save(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> update(@PathVariable Long id,
                                                     @RequestBody UserUpdateRequestDto requestDto) {
        return ResponseEntity.ok(memberService.update(id, requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {
        MemberResponseDto foundMember = memberService.findById(id);
        return ResponseEntity.ok(foundMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
