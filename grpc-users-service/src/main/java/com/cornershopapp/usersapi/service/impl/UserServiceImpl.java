package com.cornershopapp.usersapi.service.impl;

import com.cornershopapp.usersapi.grpc.GRPCUsersServiceGrpc;
import com.cornershopapp.usersapi.grpc.GetUserRequest;
import com.cornershopapp.usersapi.grpc.GetUserResponse;
import com.cornershopapp.usersapi.grpc.ListUsersRequest;
import com.cornershopapp.usersapi.grpc.ListUsersResponse;
import com.cornershopapp.usersapi.service.dto.ListUsersResponseDTO;
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
    private final GRPCUsersServiceGrpc.GRPCUsersServiceBlockingStub client;
    private final Converter<GetUserResponse, UserResponseDTO> getUserResponseToUserResponseDTOConverter;
    private final Converter<ListUsersResponse, ListUsersResponseDTO> listUsersResponseToListUsersResponseDTOConverter;

    @Autowired
    public UserServiceImpl(
            Converter<GetUserResponse, UserResponseDTO> getUserResponseToUserResponseDTOConverter,
            Converter<ListUsersResponse, ListUsersResponseDTO> listUsersResponseToListUsersResponseDTOConverter
    ) {
        // Change localhost with the IP address where the server is running
        // or leave this way if the GoLang server is running on the same machine as the client
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        this.client = GRPCUsersServiceGrpc.newBlockingStub(channel);
        this.getUserResponseToUserResponseDTOConverter = getUserResponseToUserResponseDTOConverter;
        this.listUsersResponseToListUsersResponseDTOConverter = listUsersResponseToListUsersResponseDTOConverter;
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
    public ListUsersResponseDTO getUsers() {
        try {
            ListUsersResponse listUsersResponse = client.listUsers(
                    ListUsersRequest.newBuilder().build()
            );
            return this.listUsersResponseToListUsersResponseDTOConverter.convert(listUsersResponse);
        } catch(Exception ex) {
            log.error("Error while calling getUsers", ex);
            throw ex;
        }
    }
}
