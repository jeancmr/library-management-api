package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.dto.MemberResponseDto;
import com.jeancmr.library_management.dto.UserCreateRequestDto;
import com.jeancmr.library_management.dto.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemberProfileMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    MemberProfile toEntity(UserCreateRequestDto dto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "secondName", source = "user.secondName")
    @Mapping(target = "firstSurname", source = "user.firstSurname")
    @Mapping(target = "secondSurname", source = "user.secondSurname")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "birthDate", source = "user.birthDate")
    MemberResponseDto toResponseDto(MemberProfile memberProfile);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateMemberFromDto(UserUpdateRequestDto dto, @MappingTarget MemberProfile memberProfile);
}
