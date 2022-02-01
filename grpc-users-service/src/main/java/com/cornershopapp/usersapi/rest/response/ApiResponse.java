package com.cornershopapp.usersapi.rest.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResponse<T> {
    private final T data;
}
