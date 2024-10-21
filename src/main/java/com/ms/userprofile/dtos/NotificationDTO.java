package com.ms.userprofile.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationDTO {

    private UUID userId;
    private String emailTo;
    private String subject;
    private String text;
}
