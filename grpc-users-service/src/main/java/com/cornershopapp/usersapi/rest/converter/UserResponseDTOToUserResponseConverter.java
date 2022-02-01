package com.cornershopapp.usersapi.rest.converter;

import com.cornershopapp.usersapi.rest.response.UserResponse;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserResponseDTOToUserResponseConverter implements Converter<UserResponseDTO, UserResponse> {
    @Override
    public UserResponse convert(UserResponseDTO source) {
        if (source == null) {
            return null;
        }
        return UserResponse.builder()
                .userId(source.getUserId())
                .age(source.getAge())
                .name(source.getName())
                .phoneNumber(source.getPhoneNumber())
                .build();
    }
}
