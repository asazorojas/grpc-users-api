package com.cornershopapp.usersapi.service.converter;

import com.cornershopapp.usersapi.grpc.GetUserResponse;
import com.cornershopapp.usersapi.service.dto.UserResponseDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GetUserResponseToUserResponseDTOConverter implements Converter<GetUserResponse, UserResponseDTO> {
    @Override
    public UserResponseDTO convert(GetUserResponse source) {
        return UserResponseDTO
                .builder()
                .userId(source.getUserMessage().getUserId())
                .age(source.getUserMessage().getAge())
                .name(source.getUserMessage().getName())
                .phoneNumber(source.getUserMessage().getPhoneNumber())
                .build();
    }
}
