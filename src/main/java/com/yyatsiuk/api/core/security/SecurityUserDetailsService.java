package com.yyatsiuk.api.core.security;

import com.yyatsiuk.api.core.security.dto.SecuredUserDTO;
import com.yyatsiuk.api.core.security.jwt.JwtUserFactory;
import com.yyatsiuk.api.core.security.service.UserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserSecurityService userSecurityService;

    public SecurityUserDetailsService(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SecuredUserDTO userDTO = userSecurityService.findByEmail(email);
        return JwtUserFactory.userToJwtUser(userDTO);
    }
}


