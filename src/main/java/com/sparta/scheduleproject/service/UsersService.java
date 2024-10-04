package com.sparta.scheduleproject.service;

import com.sparta.scheduleproject.dto.UsersRequestDto;
import com.sparta.scheduleproject.dto.UsersResponseDto;
import com.sparta.scheduleproject.entity.Users;
import com.sparta.scheduleproject.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    // 유저 정보 작성
    public UsersResponseDto createUser(UsersRequestDto requestDto) {
        Users users = new Users();
        users.setUserId(requestDto.getUserId());
        users.setName(requestDto.getName());
        users.setEmail(requestDto.getEmail());
        users.setPassword(requestDto.getPassword());

        Users saveUser = usersRepository.save(users);
        return new UsersResponseDto(saveUser);
    }

    // 유저 정보 조회
    public UsersResponseDto getUser(String userId, String password) {
        return usersRepository.findUser(userId, password);
    }

    // 유저 정보 수정
    public String updateUser(String userId, String password, UsersRequestDto requestDto) {
        Users users = usersRepository.findByIdAndPassword(userId, password);
        if(users != null) {
            usersRepository.update(userId, password, requestDto);
            return userId;
        } else {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        }
    }

    // 유저 정보 삭제
    public String deleteUser(String userId, String password) {
        Users users = usersRepository.findByIdAndPassword(userId, password);
        if(users != null) {
            usersRepository.delete(userId, password);
            return userId;
        } else {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        }
    }
}
