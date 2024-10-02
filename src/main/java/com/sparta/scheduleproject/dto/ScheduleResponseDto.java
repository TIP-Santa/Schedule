package com.sparta.scheduleproject.dto;

import com.sparta.scheduleproject.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long scheduleKey;
    private String name;
    private String schedule;
    private String date;
    private String createDate;
    private String modifiedDate;
    private String password;
    private String userID;

    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleKey = schedule.getScheduleKey();
        this.name = schedule.getName();
        this.schedule = schedule.getSchedule();
        this.date = schedule.getDate();
        this.password = schedule.getPassword();
        this.createDate = schedule.getCreateDate();
        this.modifiedDate = schedule.getModifiedDate();
        this.userID  = schedule.getUserId();
    }

    public ScheduleResponseDto(Long scheduleKey, String userId, String name, String date, String schedule, String createDate, String modifiedDate) {
        this.scheduleKey = scheduleKey;
        this.userID = userId;
        this.name = name;
        this.date = date;
        this.schedule = schedule;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
