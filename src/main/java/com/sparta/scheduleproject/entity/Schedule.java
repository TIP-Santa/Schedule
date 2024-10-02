package com.sparta.scheduleproject.entity;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long scheduleKey;
    private String name;
    private String schedule;
    private String date;
    private String password;
    private String createDate;
    private String modifiedDate;
    private String userId;

    public Schedule(ScheduleRequestDto requestDto) {
        this.name = requestDto.getName();
        this.schedule = requestDto.getSchedule();
        this.date = requestDto.getDate();
        this.password = requestDto.getPassword();
        this.userId = requestDto.getUserId();
    }

    public void update(ScheduleRequestDto requestDto){
        this.name = requestDto.getName();
        this.schedule = requestDto.getSchedule();
        this.date = requestDto.getDate();
        this.password = requestDto.getPassword();
    }
}
