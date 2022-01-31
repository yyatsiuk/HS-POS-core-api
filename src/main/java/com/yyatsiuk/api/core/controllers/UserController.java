package com.yyatsiuk.api.core.controllers;

import com.yyatsiuk.api.core.models.dto.UserDto;
import com.yyatsiuk.api.core.models.request.UserCreateRequest;
import com.yyatsiuk.api.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest payload) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("${id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(new UserDto());
    }

}
