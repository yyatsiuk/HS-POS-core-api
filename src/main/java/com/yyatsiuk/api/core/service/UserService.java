package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.models.dto.UserDto;

public interface UserService {

    UserDto findById(Long id);

    Long save(UserDto userDto);

    UserDto update(UserDto userDto);

}
