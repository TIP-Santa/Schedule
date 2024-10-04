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
    // 입력 형식 : {"userId" : "userid", "name" : "name", "date" : "date", "schedule" : "schedule", "password" : "password"}
    // URL : localhost:8080/schedule
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    // 일정 조회
    // requestParam(required = false)로 빠진 데이터가 있어도 조회 가능
    // 전체 조회 URL : localhost:8080/schedule
    // 특정 일자 조회 : localhost:8080/schedule?date={date}
    // 특정 작성자 조회 : localhost:8080/schedule?name={name}
    // 특정 ID 조회 : localhost:8080/schedule?userid={userid}
    // 다중 옵션 조회(예를 들어 일자 및 사용자 조회) : localhost:8080/schedule?date={date}&name={name}
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
    // URL : localhost:8080/schedule?schedulekey={schedulekey}&password={password}
    @PutMapping
    public Long modifySchedule(@RequestParam Long scheduleKey, @RequestParam String password, @RequestParam ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(scheduleKey, password, requestDto);
    }

    // 일정 삭제
    // id(schedule_key)와 password 가 필요
    // URL : localhost:8080/schedule?schedulekey={schedulekey}&password={password}
    @DeleteMapping
    public Long deleteSchedule(@RequestParam Long scheduleKey, @RequestParam String password) {
        return scheduleService.deleteSchedule(scheduleKey, password);
    }
}
