package com.sparta.scheduleproject.dto;

import lombok.Getter;

@Getter
public class UsersRequestDto {
    private int userKey;
    private String userId;
    private String name;
    private String email;
    private String password;
}
