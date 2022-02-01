package com.cornershopapp.usersapi.service;

import com.cornershopapp.usersapi.service.dto.UserRequestDTO;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO getUser(UserRequestDTO userRequest);
}
