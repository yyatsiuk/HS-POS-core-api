package com.yyatsiuk.api.core.security.jwt;

import com.yyatsiuk.api.core.enumerations.UserStatus;
import com.yyatsiuk.api.core.security.dto.JwtUser;
import com.yyatsiuk.api.core.security.dto.SecuredUserDTO;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JwtUserFactory {

    public static JwtUser userToJwtUser(SecuredUserDTO user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                new String(user.getPassword()),
                user.getStatus() == UserStatus.ACTIVE,
                true,
                true,
                true,
                mapToGrandAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrandAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}