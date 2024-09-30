package com.sparta.scheduleproject.controller;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;
    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "Insert into schedule(name, date, schedule, password) values(?,?,?,?)";

        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, schedule.getName());
                    preparedStatement.setString(2, schedule.getDate());
                    preparedStatement.setString(3, schedule.getSchedule());
                    preparedStatement.setString(4, schedule.getPassword());
                    return preparedStatement;
                }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        ScheduleResponseDto ResponseDto = new ScheduleResponseDto(schedule);
        return ResponseDto;
    }

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getSchedules() {

        String sql = "select * from schedule";
        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("schedule_key");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String schedule = rs.getString("schedule");
                String password = rs.getString("password");
                String createDate = rs.getString("create_date");
                String modifiedDate = rs.getString("modified_date");
                return new ScheduleResponseDto(id, name, date, schedule, password, createDate, modifiedDate);
            }
        });
    }

    @PutMapping("schedule/{id}")
    public Long modifySchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = findById(id);
        if (schedule != null) {
            String sql = "update schedule set name = ?, date = ?, schedule = ?, password = ?, modified_date = ? where schedule_key = ?";
            jdbcTemplate.update(sql, requestDto.getName(), requestDto.getDate(), requestDto.getSchedule(), requestDto.getPassword(), LocalDateTime.now(), id);

            return id;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/schedule/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        Schedule schedule = findById(id);
        if(schedule != null) {
            String sql = "delete from schedule where id = ?";
            jdbcTemplate.update(sql, id);
            return id;
        } else {
            throw new IllegalArgumentException("해당 스케줄은 존재하지 않습니다.");
        }
    }

    private Schedule findById(Long id) {
        String sql = "select * from schedule where schedule_key = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getLong("schedule_key"));
                schedule.setName(resultSet.getString("name"));
                schedule.setDate(resultSet.getString("date"));
                schedule.setSchedule(resultSet.getString("schedule"));
                schedule.setPassword(resultSet.getString("password"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
