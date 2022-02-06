package com.yyatsiuk.api.core.security.service;


import com.yyatsiuk.api.core.security.dto.SecuredUserDTO;

public interface UserSecurityService {

    SecuredUserDTO findByEmail(String username);

    SecuredUserDTO findById(Long id);

    boolean isActive(String email);

}
