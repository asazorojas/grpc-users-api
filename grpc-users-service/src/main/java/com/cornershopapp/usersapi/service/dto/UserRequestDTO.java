package com.cornershopapp.usersapi.service.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequestDTO {
    private final String userId;
}
