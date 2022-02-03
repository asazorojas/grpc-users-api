package com.cornershopapp.usersapi.service.impl;

import com.cornershopapp.usersapi.grpc.GetUserRequest;
import com.cornershopapp.usersapi.grpc.GetUserResponse;
import com.cornershopapp.usersapi.grpc.ListUsersRequest;
import com.cornershopapp.usersapi.grpc.ListUsersResponse;
import com.cornershopapp.usersapi.grpc.UserGrpc;
import com.cornershopapp.usersapi.grpc.UserMessage;
import com.cornershopapp.usersapi.service.dto.UserRequestDTO;
import com.cornershopapp.usersapi.service.UserService;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserGrpc.UserBlockingStub client;
    private final Converter<GetUserResponse, UserResponseDTO> getUserResponseToUserResponseDTOConverter;

    @Autowired
    public UserServiceImpl(Converter<GetUserResponse, UserResponseDTO> getUserResponseToUserResponseDTOConverter) {
        // Change localhost with the IP address where the server is running
        // or leave this way if the GoLang server is running on the same machine as the client
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        this.client = UserGrpc.newBlockingStub(channel);
        this.getUserResponseToUserResponseDTOConverter = getUserResponseToUserResponseDTOConverter;
    }

    @Override
    public UserResponseDTO getUser(UserRequestDTO userRequest) {
        try {
            GetUserResponse userResponse = client.getUser(
                    GetUserRequest
                            .newBuilder()
                            .setUserId(userRequest.getUserId())
                            .build()
            );
            return getUserResponseToUserResponseDTOConverter.convert(userResponse);
        } catch (StatusRuntimeException ex) {
            log.error("Error while calling getUser", ex);
            throw ex;
        }
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        try {
            ListUsersResponse listUsersResponse = client.listUsers(
                    ListUsersRequest.newBuilder().build()
            );
            return listUsersResponse.getUserMessagesList().stream()
                    .map(userMessage ->
                            UserResponseDTO.builder()
                                    .userId(userMessage.getUserId())
                                    .age(userMessage.getAge())
                                    .name(userMessage.getName())
                                    .phoneNumber(userMessage.getPhoneNumber())
                                    .build()
                    )
                    .collect(Collectors.toList());
        } catch(Exception ex) {
            log.error("Error while calling getUsers", ex);
            throw ex;
        }
    }
}
