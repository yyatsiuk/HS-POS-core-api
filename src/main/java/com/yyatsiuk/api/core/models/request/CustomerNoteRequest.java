package com.yyatsiuk.api.core.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerNoteRequest {

    private String content;
    private Long senderId;

}

