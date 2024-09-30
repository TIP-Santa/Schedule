package com.sparta.scheduleproject.entity;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String name;
    private String schedule;
    private String date;
    private String createDate;
    private String modifiedDate;

    public Schedule(ScheduleRequestDto requestDto) {
        this.name = requestDto.getName();
        this.schedule = requestDto.getSchedule();
        this.date = requestDto.getDate();
        this.createDate = LocalDateTime.now().toString();
        this.modifiedDate = createDate;
    }

    public void update(ScheduleRequestDto requestDto){
        this.name = requestDto.getName();
        this.schedule = requestDto.getSchedule();
        this.date = requestDto.getDate();
        this.modifiedDate = LocalDateTime.now().toString();
    }
}
