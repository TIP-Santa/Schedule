package com.sparta.scheduleproject.controller;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;
    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.scheduleService = new ScheduleService(jdbcTemplate);
    }

    // 일정 작성
    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    // 일정 조회
    // requestParam 방식을 사용하여 조회 조건인 name이나 date가 없어도 조회가 가능
    // 전체 일정 조회 : localhost:8080/api/schedule
    // 특정 사용자 조회 : localhost:8080/api/schedule?name={name}
    // 특정 일정 조회 : localhost:8080/api/schedule?date={YYYY-MM-DD}
    // 특정 사용자의 특정 일정 조회 : localhost:8080/api/schedule?name={name}&date={YYYY-MM-DD}
    // 특정 단건 일정 조회 : localhost:8080/api/schedule?id={id}
    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getSchedule(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String id) {
        return scheduleService.getSchedules(name , date, id);
    }

    // 일정 수정
    // 작성자명과 일정만 수정 가능
    // 비밀번호가 일치할 경우에만 수정 가능
    // 입력 형식 {name : {name}, schedule :{schedule}, password : {password}}
    @PutMapping("schedule/{id}")
    public Long modifySchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    // 일정 삭제
    // id(schedule_key)와 password 가 필요
    // 입력 형식 : localhost:8080/api/schedule/{id}/password/{password}
    @DeleteMapping("/schedule/{id}/password/{password}")
    public Long deleteSchedule(@PathVariable Long id, @PathVariable String password) {
        return scheduleService.deleteSchedule(id, password);
    }
}
