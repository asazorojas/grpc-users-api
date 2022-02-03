package com.cornershopapp.usersapi.rest.controllers;

import com.cornershopapp.usersapi.rest.response.ApiResponse;
import com.cornershopapp.usersapi.rest.response.UserResponse;
import com.cornershopapp.usersapi.service.dto.UserRequestDTO;
import com.cornershopapp.usersapi.service.UserService;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class UsersController {
    private final UserService userService;
    private final Converter<UserResponseDTO, UserResponse> userResponseDTOToUserResponseConverter;

    @Autowired
    public UsersController(UserService userService, Converter<UserResponseDTO, UserResponse> userResponseDTOToUserResponseConverter) {
        this.userService = userService;
        this.userResponseDTOToUserResponseConverter = userResponseDTOToUserResponseConverter;
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer userId) {
        UserResponseDTO user = this.userService.getUser(
                UserRequestDTO
                        .builder()
                        .userId(String.valueOf(userId))
                        .build()
        );
        UserResponse userResponse = this.userResponseDTOToUserResponseConverter.convert(user);
        return ResponseEntity.ok(
                ApiResponse.<UserResponse>builder()
                        .data(userResponse)
                        .build()
        );
    }

    @GetMapping(value = "/users")
    public ResponseEntity<ApiResponse<?>> getUserById() {
        // Just for the sake of the demo we're exposing this DTO from the service layer to the presentation layer
        List<UserResponseDTO> users = userService.getUsers();
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .data(users)
                        .build()
        );
    }
}
