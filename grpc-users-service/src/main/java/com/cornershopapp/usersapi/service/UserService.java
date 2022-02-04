package com.cornershopapp.usersapi.service;

import com.cornershopapp.usersapi.service.dto.ListUsersResponseDTO;
import com.cornershopapp.usersapi.service.dto.UserRequestDTO;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;
import java.util.List;

public interface UserService {
    UserResponseDTO getUser(UserRequestDTO userRequest);
    ListUsersResponseDTO getUsers();
}
