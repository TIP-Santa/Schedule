package com.sparta.scheduleproject.dto;

import com.sparta.scheduleproject.entity.Users;
import lombok.Getter;

@Getter
public class UsersResponseDto {
    private int userKey;
    private String userId;
    private String name;
    private String email;
    private String password;
    private String createDate;
    private String modifiedDate;


    public UsersResponseDto(Users users) {
        this.userKey = users.getUserKey();
        this.userId = users.getUserId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.password = users.getPassword();
        this.createDate = users.getCreateDate();
        this.modifiedDate = users.getModifiedDate();
    }

    public UsersResponseDto(String userId, String name, String email){
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
