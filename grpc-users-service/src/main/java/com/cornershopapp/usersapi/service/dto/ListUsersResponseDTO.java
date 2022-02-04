package com.cornershopapp.usersapi.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListUsersResponseDTO {
    private final List<UserResponseDTO> users;
}
