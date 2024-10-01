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

    // 일정 작성
    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 작성에 필요한 데이터 : name, date, schedule, password
        // 작성하지 않고 자동으로 들어가는 데이터 : id(schedule_key), create_date, modified_date
        String sql = "Insert into schedule(name, date, schedule, password) values(?,?,?,?)";

        // 위 코드의 ?부분에 들어가는 데이터
        // 각 숫자는 ?의 위치를 의미
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

        StringBuilder sql = new StringBuilder("select * from schedule where 1=1");
        List<Object> params = new ArrayList<>();
        // name 데이터가 존재한다면 name을 기준 조건 추가
        if (name != null) {
            sql.append(" and name = ?");
            params.add(name);
        }
        // date 데이터가 존재한다면 date 기준 조건 추가
        if (date != null) {
            sql.append(" and date = ?");
            params.add(date);
        }
        // id 데이터가 존재한다면 id 기준 조건 추가
        if (id != null) {
            sql.append(" and schedule_key = ?");
            params.add(id);
        }
        sql.append(" order by date desc");
        return jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("schedule_key");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String schedule = rs.getString("schedule");
                String createDate = rs.getString("create_date");
                String modifiedDate = rs.getString("modified_date");
                return new ScheduleResponseDto(id, name, date, schedule, createDate, modifiedDate);
            }
        });





//        String sql = new String("select * from schedule where name = ? and date = ?").toString();
//        return jdbcTemplate.query(sql, new Object[]{name, date}, new RowMapper<ScheduleResponseDto>() {
//            @Override
//            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                Long id = rs.getLong("schedule_key");
//                String name = rs.getString("name");
//                String date = rs.getString("date");
//                String schedule = rs.getString("schedule");
//                String password = rs.getString("password");
//                String createDate = rs.getString("create_date");
//                String modifiedDate = rs.getString("modified_date");
//                return new ScheduleResponseDto(id, name, date, schedule, password, createDate, modifiedDate);
//            }
//        });
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
