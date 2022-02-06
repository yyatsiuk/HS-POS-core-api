package com.yyatsiuk.api.core.controllers;

import com.yyatsiuk.api.core.exceptions.EntityNotFoundException;
import com.yyatsiuk.api.core.models.request.LoginRequest;
import com.yyatsiuk.api.core.models.response.LoginResponse;
import com.yyatsiuk.api.core.security.dto.SecuredUserDTO;
import com.yyatsiuk.api.core.security.jwt.JwtTokenProvider;
import com.yyatsiuk.api.core.security.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserSecurityService userSecurityService;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserSecurityService userSecurityService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userSecurityService = userSecurityService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest requestUser) {
        try {
            return authenticate(requestUser);
        } catch (DisabledException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException | EntityNotFoundException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


    private ResponseEntity<LoginResponse> authenticate(LoginRequest loginRequest) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginRequest.getUsername(), new String(loginRequest.getPassword())));

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        SecuredUserDTO securedUserDTO = userSecurityService
                .findByEmail(loginRequest.getUsername());

        String accessToken = jwtTokenProvider.createToken(securedUserDTO.getId(), securedUserDTO.getEmail(), securedUserDTO.getRoles());

        securedUserDTO.setAccessToken(accessToken);

        return ResponseEntity.ok(new LoginResponse(accessToken));
    }

}
