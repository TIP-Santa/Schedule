package com.sparta.scheduleproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Users {
    private String userId;
    private String name;
    private String email;
    private String password;
    private int userKey;
    private String createDate;
    private String modifiedDate;
}
