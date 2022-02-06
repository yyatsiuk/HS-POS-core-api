package com.yyatsiuk.api.core.models.mappers;

import com.yyatsiuk.api.core.models.dto.UserDto;
import com.yyatsiuk.api.core.models.entities.User;
import com.yyatsiuk.api.core.models.request.UserCreateRequest;
import com.yyatsiuk.api.core.models.request.UserUpdateRequest;
import com.yyatsiuk.api.core.security.dto.SecuredUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto fromEntityToDto(User entity);

    User fromDtoToEntity(UserDto dto);

    UserDto fromCreateRequestToDto(UserCreateRequest request);

    UserDto fromUpdateRequestToDto(UserUpdateRequest request);

    @Mapping(target = "roles", expression = "java(entity.getRoles().stream().map(com.yyatsiuk.api.core.models.entities.Role::getRoleName).toList())")
    SecuredUserDTO fromEntityToSecureDto(User entity);

}
