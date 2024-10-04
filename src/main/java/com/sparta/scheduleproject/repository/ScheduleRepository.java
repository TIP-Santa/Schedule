package com.sparta.scheduleproject.repository;

import com.sparta.scheduleproject.dto.ScheduleRequestDto;
import com.sparta.scheduleproject.dto.ScheduleResponseDto;
import com.sparta.scheduleproject.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // DB 저장
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 작성에 필요한 데이터 : userId, name, date, schedule, password
        // 작성하지 않고 자동으로 들어가는 데이터 : id(schedule_key), create_date, modified_date
        String sql = "Insert into schedule(user_id, name, date, schedule, password) values(?,?,?,?,?)";
        // 위 코드의 ?부분에 들어가는 데이터
        // 각 숫자는 ?의 위치를 의미
        jdbcTemplate.update( con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, schedule.getUserId());
            preparedStatement.setString(2, schedule.getName());
            preparedStatement.setString(3, schedule.getDate());
            preparedStatement.setString(4, schedule.getSchedule());
            preparedStatement.setString(5, schedule.getPassword());
            return preparedStatement;
        }, keyHolder);
        Long key = keyHolder.getKey().longValue();
        schedule.setScheduleKey(key);
        return schedule;
    }

    // DB 조회
    public List<ScheduleResponseDto> findAll(String userId, String name, String date, Long scheduleKey, Long page, Long scheduleList) {
        StringBuilder sql = new StringBuilder("select * from schedule where 1=1");
        List<Object> params = new ArrayList<>();

        // sql 순서 : select - from - where - order by - limit - offset
        //  userId 데이터가 존재한다면 uesrId 기준 조건 추가
        if (userId != null) {
            sql.append(" and user_id = ?");
            params.add(userId);
        }
        // name 데이터가 존재한다면 name 기준 조건 추가
        if (name != null) {
            sql.append(" and name = ?");
            params.add(name);
        }

        // date 데이터가 존재한다면 date 기준 조건 추가
        if (date != null) {
            sql.append(" and date = ?");
            params.add(date);
        }

        // scheduleKey 데이터가 존재한다면 scheduleKey 기준 조건 추가
        if (scheduleKey != null) {
            sql.append(" and schedule_key = ?");
            params.add(scheduleKey);
        }

        // date 를 기준으로 내림차순 정렬
        sql.append(" order by date desc");

        // page 당 출력되는 schedule 갯수
        if(scheduleList != null) {
            sql.append(" limit ?");
            params.add(scheduleList);
        }

        // page 조건 추가
        if(page != null) {
            sql.append(" offset ?");
            params.add((page-1) * scheduleList);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long scheduleKey = rs.getLong("schedule_key");
                String name = rs.getString("name");
                String date = rs.getString("date");
                String schedule = rs.getString("schedule");
                String createDate = rs.getString("create_date");
                String modifiedDate = rs.getString("modified_date");
                String userId = rs.getString("user_id");
                return new ScheduleResponseDto(scheduleKey, userId, name, date, schedule, createDate, modifiedDate);
            }
        });
    }

    public void update(Long scheduleKey, String password, ScheduleRequestDto requestDto) {
        // sql db에 저장된 password 를 확인
        String schedulePasswordSql = "select password from schedule where schedule_key = ?";
        String schedulePassword = jdbcTemplate.queryForObject(schedulePasswordSql, new Object[]{scheduleKey}, String.class);
        // 저장된 password 와 입력된 password 를 비교
        if(schedulePassword != null && schedulePassword.equals(password)) {
            // password 가 일치할 경우 수정 로직 실행
            String sql = "update schedule set name = ?, schedule = ? where schedule_key = ?";
            jdbcTemplate.update(sql, requestDto.getName(), requestDto.getSchedule(), scheduleKey);
        } else {
            // password 불일치할 경우 예외처리
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void delete(Long id, String password) {
        // sql db에 저장된 password 를 확인
        String schedulePasswordSql = "select password from schedule where schedule_key = ?";
        String schedulePassword = jdbcTemplate.queryForObject(schedulePasswordSql, new Object[]{id}, String.class);
        // 저장된 password 와 입력된 password 를 비교
        if(schedulePassword != null && schedulePassword.equals(password)) {
            // password 가 일치할 경우 삭제 로직 실행
            String sql = "delete from schedule where schedule_key = ?";
            jdbcTemplate.update(sql, id);
        } else {
            // password 불일치할 경우 예외처리
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public Schedule findById(Long scheduleKey) {
        String sql = "select * from schedule where schedule_key = ?";
        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleKey(resultSet.getLong("schedule_key"));
                schedule.setName(resultSet.getString("name"));
                schedule.setDate(resultSet.getString("date"));
                schedule.setSchedule(resultSet.getString("schedule"));
                schedule.setPassword(resultSet.getString("password"));
                return schedule;
            } else {
                return null;
            }
        }, scheduleKey);
    }
}
