package com.example.chatservice.payload.request;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatePost {
    private String accessToken;
}