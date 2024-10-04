package com.sparta.scheduleproject.repository;

import com.sparta.scheduleproject.dto.UsersRequestDto;
import com.sparta.scheduleproject.dto.UsersResponseDto;
import com.sparta.scheduleproject.entity.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 유저 정보 저장
    public Users save(Users users) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // 작성에 필요한 데이터 : id, name, password, email
        // 작성하지 않고 자동으로 들어가는 데이터 : user_key, create_date, modified_date
        String sql = "insert into users(user_id, name, password, email) values(?,?,?,?)";

        // 위 코드의 ?부분에 들어가는 데이터
        // 각 숫자는 ?의 위치를 의미
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, users.getUserId());
            preparedStatement.setString(2, users.getName());
            preparedStatement.setString(3, users.getPassword());
            preparedStatement.setString(4, users.getEmail());
            return preparedStatement;
        }, keyHolder);
        int key = keyHolder.getKey().intValue();
        users.setUserKey(key);

        return users;
    }

    // 유저 정보 조회
    public UsersResponseDto findUser(String userId, String password) {
        String sql = "select * from users where user_id = ? and password = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, password}, new RowMapper<UsersResponseDto>(){
            @Override
            public UsersResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            int userKey = rs.getInt("user_key");
            String userId = rs.getString("user_id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String createDate = rs.getString("create_date");
            return new UsersResponseDto(userKey, userId, name, email, createDate);
            }
        });
    }

    // 유저 정보 수정
    public void update(String userId, String password, UsersRequestDto RequestDto) {
        String userPasswordSql = "select password from users where user_id = ?";
        String userPassword = jdbcTemplate.queryForObject(userPasswordSql, new Object[]{userId}, String.class);
        System.out.println(userPassword);
        if(userPassword != null && userPassword.equals(password)) {
            String sql = "update users set name = ?, email = ? where user_id = ?";
            jdbcTemplate.update(sql, RequestDto.getName(), RequestDto.getEmail(), userId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 유저 정보 삭제
    public void delete(String userId, String password) {
        String userPasswordSql = "select password from users where user_id = ?";
        String userPassword = jdbcTemplate.queryForObject(userPasswordSql, new Object[]{userId}, String.class);

        if(userPassword != null && userPassword.equals(password)) {
            String sql = "delete from users where user_id = ?";
            jdbcTemplate.update(sql, userId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }



    public Users findByIdAndPassword(String userId, String password) {
        String sql = "select * from users where user_id = ? and password = ?";
        return jdbcTemplate.query(sql, new Object[]{userId, password}, resultSet -> {
            if(resultSet.next()) {
                Users users = new Users();
                users.setUserId(resultSet.getString("user_id"));
                users.setName(resultSet.getString("name"));
                users.setPassword(resultSet.getString("password"));
                users.setEmail(resultSet.getString("email"));
                return users;
            } else {
                return null;
            }
        });
    }
}
