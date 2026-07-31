package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.MemberProfile;
import com.jeancmr.library_management.dto.MemberCreateRequestDto;
import com.jeancmr.library_management.dto.MemberResponseDto;
import com.jeancmr.library_management.dto.MemberUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemberProfileMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    MemberProfile toEntity(MemberCreateRequestDto dto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "secondName", source = "user.secondName")
    @Mapping(target = "firstSurname", source = "user.firstSurname")
    @Mapping(target = "secondSurname", source = "user.secondSurname")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "birthDate", source = "user.birthDate")
    MemberResponseDto toResponseDto(MemberProfile memberProfile);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateMemberFromDto(MemberUpdateRequestDto dto, @MappingTarget MemberProfile memberProfile);
}
