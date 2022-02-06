package com.yyatsiuk.api.core.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty(message = "Username must not be null or empty")
    private String username;

    @NotEmpty(message = "Password must not be null or empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private char[] password;

}
