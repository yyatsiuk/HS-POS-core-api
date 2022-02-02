package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.exceptions.EntityNotFoundException;
import com.yyatsiuk.api.core.models.dto.UserDto;
import com.yyatsiuk.api.core.models.entities.User;
import com.yyatsiuk.api.core.models.mappers.UserMapper;
import com.yyatsiuk.api.core.repository.UserRepository;
import com.yyatsiuk.api.core.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id: {0} not found";
    private static final String NULL_USER_ERROR_MESSAGE = "User DTO cannot be null";

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::fromEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE, id));
    }

    @Override
    public Long save(UserDto userDto) {
        Assert.notNull(userDto, NULL_USER_ERROR_MESSAGE);

        User user = userMapper.fromDtoToEntity(userDto);
        return userRepository.save(user).getId();
    }

    @Override
    public UserDto update(UserDto userDto) {
        Assert.notNull(userDto, NULL_USER_ERROR_MESSAGE);

        User user = userMapper.fromDtoToEntity(userDto);
        return userMapper.fromEntityToDto(userRepository.save(user));
    }

}
