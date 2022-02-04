package com.cornershopapp.usersapi.rest.converter;

import com.cornershopapp.usersapi.rest.response.ListUsersResponse;
import com.cornershopapp.usersapi.rest.response.UserResponse;
import com.cornershopapp.usersapi.service.dto.ListUsersResponseDTO;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ListUsersResponseDTOToListUsersResponseConverter implements Converter<ListUsersResponseDTO, ListUsersResponse> {

    @Override
    public ListUsersResponse convert(ListUsersResponseDTO source) {
        var users = source.getUsers().stream()
                .map(userMessage ->
                        UserResponse
                                .builder()
                                .userId(userMessage.getUserId())
                                .age(userMessage.getAge())
                                .name(userMessage.getName())
                                .phoneNumber(userMessage.getPhoneNumber())
                                .build()
                )
                .collect(Collectors.toList());
        return ListUsersResponse
                .builder()
                .users(users)
                .build();
    }
}
