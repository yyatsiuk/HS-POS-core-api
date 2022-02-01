package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.models.dto.UserDto;
import com.yyatsiuk.api.core.repository.UserRepository;
import com.yyatsiuk.api.core.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto findById(Long id) {

        return new UserDto();
    }

    @Override
    public Long save(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return new UserDto();
    }

}
