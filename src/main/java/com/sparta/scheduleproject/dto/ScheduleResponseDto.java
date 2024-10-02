package com.sparta.scheduleproject.dto;

import com.sparta.scheduleproject.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String schedule;
    private String date;
    private String createDate;
    private String modifiedDate;
    private String password;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.schedule = schedule.getSchedule();
        this.date = schedule.getDate();
        this.password = schedule.getPassword();
        this.createDate = schedule.getCreateDate();
        this.modifiedDate = schedule.getModifiedDate();

    }

    public ScheduleResponseDto(Long id, String name, String date, String schedule, String createDate, String modifiedDate) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.schedule = schedule;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
