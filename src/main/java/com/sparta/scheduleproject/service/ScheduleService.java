package com.sparta.scheduleproject.service;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.entity.Schedule;
import com.sparta.scheduleproject.repository.ScheduleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;
    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule saveSchedule =  scheduleRepository.save(schedule);
        ScheduleResponseDto ResponseDto = new ScheduleResponseDto(schedule);
        return ResponseDto;
    }

    public List<ScheduleResponseDto> getSchedules(String name, String date, String id) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAll(name, date, id);
    }

    public Long updateSchedule(Long id, ScheduleRequestDto requestDto) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            scheduleRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id, String password) {
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            scheduleRepository.delete(id, password);
            return id;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }
}