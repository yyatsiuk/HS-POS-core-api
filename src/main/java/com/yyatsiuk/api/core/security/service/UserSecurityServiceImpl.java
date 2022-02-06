package com.yyatsiuk.api.core.security.service;

import com.yyatsiuk.api.core.enumerations.UserStatus;
import com.yyatsiuk.api.core.exceptions.EntityNotFoundException;
import com.yyatsiuk.api.core.models.entities.User;
import com.yyatsiuk.api.core.models.mappers.UserMapper;
import com.yyatsiuk.api.core.repository.UserRepository;
import com.yyatsiuk.api.core.security.dto.SecuredUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserSecurityServiceImpl(UserRepository userRepository,
                                   UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public SecuredUserDTO findByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email:{0} not found", email));

        return userMapper.fromEntityToSecureDto(user);
    }

    @Override
    public SecuredUserDTO findById(Long id) throws DataAccessException, EntityNotFoundException {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id:{0} not found", id));

        return userMapper.fromEntityToSecureDto(user);

    }

    @Override
    public boolean isActive(String email) throws EntityNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email:{0} not found", email));

        return user.getStatus() == UserStatus.ACTIVE;
    }

}
