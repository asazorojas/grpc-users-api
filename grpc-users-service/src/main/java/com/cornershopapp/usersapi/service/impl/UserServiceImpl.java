package com.cornershopapp.usersapi.service.impl;

import com.cornershopapp.usersapi.grpc.GetUserRequest;
import com.cornershopapp.usersapi.grpc.GetUserResponse;
import com.cornershopapp.usersapi.grpc.UserGrpc;
import com.cornershopapp.usersapi.service.dto.UserRequestDTO;
import com.cornershopapp.usersapi.service.UserService;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
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
}
