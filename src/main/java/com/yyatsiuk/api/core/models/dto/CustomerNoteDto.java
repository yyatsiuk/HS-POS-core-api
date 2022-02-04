package com.yyatsiuk.api.core.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNoteDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String senderAvatarUrl;
    private Long senderId;
    private String senderName;

}
