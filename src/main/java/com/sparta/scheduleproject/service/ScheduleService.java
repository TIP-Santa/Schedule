package com.sparta.scheduleproject.service;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.entity.Schedule;
import com.sparta.scheduleproject.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        Schedule saveSchedule =  scheduleRepository.save(schedule);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }

    public List<ScheduleResponseDto> getSchedules(String name, String date, String id) {
        return scheduleRepository.findAll(name, date, id);
    }

    public Long updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            scheduleRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id);
        if(schedule != null) {
            scheduleRepository.delete(id, password);
            return id;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }
}