package org.heymouad.bookingmanagementsystem.mappers;


import org.heymouad.bookingmanagementsystem.dtos.RoleDto;
import org.heymouad.bookingmanagementsystem.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto roleDto);
    RoleDto toDto(Role role);
}
