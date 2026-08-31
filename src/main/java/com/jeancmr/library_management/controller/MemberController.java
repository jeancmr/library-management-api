package com.jeancmr.library_management.controller;

import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.dto.Member.MemberResponseDto;
import com.jeancmr.library_management.mapper.MemberProfileMapper;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.service.interfaces.IMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Members", description = "Operations related to members")
public class MemberController {

    private final IMemberService memberService;
    private final MemberProfileMapper  memberProfileMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting all members")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Members found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public List<MemberResponseDto> findAll() {
        return memberService.findAll().stream()
                .map(memberProfileMapper::toResponseDto).toList();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Save member")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Member saved successfully"),
                    @ApiResponse(responseCode = "409", description = "User with the same email already registered"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<MemberResponseDto> save(@Valid @RequestBody UserCreateRequestDto requestDto) {
        MemberProfile savedMember = memberService.save(requestDto);
        MemberResponseDto responseDto = memberProfileMapper.toResponseDto(savedMember);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Update member")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Member updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Member not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<MemberResponseDto> update(@PathVariable Long id,
                                                     @RequestBody UserUpdateRequestDto requestDto) {
        MemberProfile updatedMember = memberService.update(id, requestDto);
        MemberResponseDto responseDto = memberProfileMapper.toResponseDto(updatedMember);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Getting member by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Member found"),
                    @ApiResponse(responseCode = "404", description = "Member not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {
        MemberProfile foundMember =  memberService.findById(id);
        MemberResponseDto responseDto =  memberProfileMapper.toResponseDto(foundMember);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LIBRARIAN')")
    @Operation(summary = "Delete member")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "Member not found"),
                    @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
