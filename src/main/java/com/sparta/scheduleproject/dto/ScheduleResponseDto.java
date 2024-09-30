package com.sparta.scheduleproject.dto;

import com.sparta.scheduleproject.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String schedule;
    private String date;
    private String createDate;
    private String modifiedDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.schedule = schedule.getSchedule();
        this.date = schedule.getDate();
        this.createDate = LocalDateTime.now().toString();
        this.modifiedDate = this.createDate;
    }
}
