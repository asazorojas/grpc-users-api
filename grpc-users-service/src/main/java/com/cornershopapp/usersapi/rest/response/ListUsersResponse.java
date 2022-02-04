package com.cornershopapp.usersapi.rest.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ListUsersResponse {
    private final List<UserResponse> users;
}
