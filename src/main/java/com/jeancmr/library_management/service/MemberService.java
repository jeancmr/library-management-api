package com.jeancmr.library_management.service;

import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.domain.User;
import com.jeancmr.library_management.dto.MemberResponseDto;
import com.jeancmr.library_management.dto.UserCreateRequestDto;
import com.jeancmr.library_management.dto.UserUpdateRequestDto;
import com.jeancmr.library_management.enums.MemberStatus;
import com.jeancmr.library_management.enums.Role;
import com.jeancmr.library_management.exception.EmailAlreadyExistsException;
import com.jeancmr.library_management.exception.ResourceNotFoundException;
import com.jeancmr.library_management.mapper.MemberProfileMapper;
import com.jeancmr.library_management.mapper.UserMapper;
import com.jeancmr.library_management.repository.MemberRepository;
import com.jeancmr.library_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService{

    private final MemberRepository  memberRepository;
    private final UserRepository  userRepository;
    private final UserMapper  userMapper;
    private final MemberProfileMapper memberMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {

        return memberRepository.findAll()
                .stream()
                .map(memberMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        MemberProfile memberFound = memberRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(MemberProfile.class, id));

        return memberMapper.toResponseDto(memberFound);

    }

    @Override
    @Transactional
    public MemberResponseDto save(UserCreateRequestDto request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user = userMapper.toEntity(request);
        int BORROW_LIMIT = 4;
        MemberProfile memberProfile = memberMapper.toEntity(request);
        memberProfile.setStatus(MemberStatus.ACTIVE);
        memberProfile.setMembershipDate(LocalDate.now());
        memberProfile.setBorrowLimit(BORROW_LIMIT);

        user.setRoles(Set.of(Role.ROLE_MEMBER));
        user.assignMemberProfile(memberProfile);

        User savedUser = userRepository.save(user);

        return memberMapper.toResponseDto(savedUser.getMemberProfile());
    }

    @Override
    @Transactional
    public MemberResponseDto update(Long id, UserUpdateRequestDto request) {
        User existingUser = memberRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MemberProfile.class, id)).getUser();

        userMapper.updateUser(request, existingUser);
        memberMapper.updateMemberFromDto(request, existingUser.getMemberProfile());

        User updatedUser = userRepository.save(existingUser);

        return memberMapper.toResponseDto(updatedUser.getMemberProfile());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(!memberRepository.existsById(id)) {
            throw new  ResourceNotFoundException(MemberProfile.class, id);
        }
        userRepository.deleteById(id);
    }

}
