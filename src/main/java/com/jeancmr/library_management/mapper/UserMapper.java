package com.jeancmr.library_management.mapper;

import com.jeancmr.library_management.domain.User;
import com.jeancmr.library_management.dto.UserCreateRequestDto;
import com.jeancmr.library_management.dto.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "memberProfile", ignore = true)
    @Mapping(target = "librarianProfile", ignore = true)
    User toEntity(UserCreateRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "memberProfile", ignore = true)
    @Mapping(target = "librarianProfile", ignore = true)
    void updateUser(UserUpdateRequestDto userUpdateRequestDto, @MappingTarget User user);

}
