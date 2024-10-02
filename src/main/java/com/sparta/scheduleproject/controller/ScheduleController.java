package com.sparta.scheduleproject.controller;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 작성
    // 입력해야 할 데이터 : userId, name, date, schedule, password
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    // 일정 조회
    // requestParam(required = false)로 빠진 데이터가 있어도 조회 가능
    @GetMapping
    public List<ScheduleResponseDto> getSchedule(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long scheduleKey) {
        return scheduleService.getSchedules(userId, name , date, scheduleKey);
    }

    // 일정 수정
    // 작성자명과 일정만 수정 가능
    // 비밀번호가 일치할 경우에만 수정 가능
    // 입력 형식 {name : {name}, schedule :{schedule}, password : {password}}
    @PutMapping("{scheduleKey}/password/{pasword}")
    public Long modifySchedule(@PathVariable Long scheduleKey, @PathVariable String password, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(scheduleKey, password, requestDto);
    }

    // 일정 삭제
    // id(schedule_key)와 password 가 필요
    // 입력 형식 : localhost:8080/schedule/{id}/password/{password}
    @DeleteMapping("/{scheduleKey}/password/{password}")
    public Long deleteSchedule(@PathVariable Long scheduleKey, @PathVariable String password) {
        return scheduleService.deleteSchedule(scheduleKey, password);
    }
}
