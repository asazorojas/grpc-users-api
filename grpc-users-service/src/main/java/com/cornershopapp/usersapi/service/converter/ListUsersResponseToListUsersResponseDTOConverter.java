package com.cornershopapp.usersapi.service.converter;

import com.cornershopapp.usersapi.grpc.ListUsersResponse;
import com.cornershopapp.usersapi.service.dto.ListUsersResponseDTO;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListUsersResponseToListUsersResponseDTOConverter implements Converter<ListUsersResponse, ListUsersResponseDTO> {

    @Override
    public ListUsersResponseDTO convert(ListUsersResponse source) {
        var users = source.getUserMessagesList().stream()
                .map(userMessage ->
                        UserResponseDTO
                                .builder()
                                .userId(userMessage.getUserId())
                                .age(userMessage.getAge())
                                .name(userMessage.getName())
                                .phoneNumber(userMessage.getPhoneNumber())
                                .build()
                )
                .collect(Collectors.toList());
        return ListUsersResponseDTO
                .builder()
                .users(users)
                .build();
    }
}
