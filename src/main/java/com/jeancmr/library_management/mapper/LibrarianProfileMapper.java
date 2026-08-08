package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.LibrarianProfile;
import com.jeancmr.library_management.dto.Librarian.LibrarianResponseDto;
import com.jeancmr.library_management.security.dto.UserCreateRequestDto;
import com.jeancmr.library_management.security.dto.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibrarianProfileMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    LibrarianProfile toEntity(UserCreateRequestDto dto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "secondName", source = "user.secondName")
    @Mapping(target = "firstSurname", source = "user.firstSurname")
    @Mapping(target = "secondSurname", source = "user.secondSurname")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "birthDate", source = "user.birthDate")
    LibrarianResponseDto toResponseDto(LibrarianProfile librarianProfile);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateLibrarianFromDto(UserUpdateRequestDto dto, @MappingTarget LibrarianProfile librarianProfile);
}
