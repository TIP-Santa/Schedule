package com.sparta.scheduleproject.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String userId;
    private String name;
    private String schedule;
    private String date;
    private String password;
}
