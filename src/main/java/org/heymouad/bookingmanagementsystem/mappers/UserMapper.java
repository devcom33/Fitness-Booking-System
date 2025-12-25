package org.heymouad.bookingmanagementsystem.mappers;

import org.heymouad.bookingmanagementsystem.dtos.UserDto;
import org.heymouad.bookingmanagementsystem.dtos.UserRegistrationRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.UserResponseDto;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toUserResponseDto(User user);
    UserDto toUserDto(User user);

    @Mapping(target = "role", ignore = true)
    User toUser(UserRegistrationRequestDto userRegistrationRequestDto);

    @Mapping(target = "role", ignore = true)
    User toUserEntity(UserResponseDto userResponseDto);
}
