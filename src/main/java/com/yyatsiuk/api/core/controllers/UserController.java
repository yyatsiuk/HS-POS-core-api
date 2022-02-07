package com.yyatsiuk.api.core.controllers;

import com.yyatsiuk.api.core.models.dto.UserDto;
import com.yyatsiuk.api.core.models.mappers.UserMapper;
import com.yyatsiuk.api.core.models.request.UserCreateRequest;
import com.yyatsiuk.api.core.models.request.UserUpdateRequest;
import com.yyatsiuk.api.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserCreateRequest payload) {
        UserDto userDto = userMapper.fromCreateRequestToDto(payload);

        userService.save(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserUpdateRequest payload) {
        UserDto userDto = userMapper.fromUpdateRequestToDto(payload);

        return ResponseEntity.ok(userService.update(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

}
