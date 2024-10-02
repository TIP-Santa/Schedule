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

    // 생성 (POST)
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        Schedule saveSchedule =  scheduleRepository.save(schedule);
        ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
        return responseDto;
    }

    // 조회 (GET)
    public List<ScheduleResponseDto> getSchedules(String userId, String name, String date, Long scheduleKey) {
        return scheduleRepository.findAll(userId, name, date, scheduleKey);
    }

    // 수정 (PUT)
    public Long updateSchedule(Long scheduleKey, String password, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleKey);
        if (schedule != null) {
            scheduleRepository.update(scheduleKey, password, requestDto);
            return scheduleKey;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }

    // 삭제 (DELETE)
    public Long deleteSchedule(Long scheduleKey, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleKey);
        if(schedule != null) {
            scheduleRepository.delete(scheduleKey, password);
            return scheduleKey;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }
}