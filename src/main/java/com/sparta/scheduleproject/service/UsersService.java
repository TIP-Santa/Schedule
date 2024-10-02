package com.sparta.scheduleproject.service;

import com.sparta.scheduleproject.dto.UsersRequestDto;
import com.sparta.scheduleproject.dto.UsersResponseDto;
import com.sparta.scheduleproject.entity.Users;
import com.sparta.scheduleproject.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public UsersResponseDto createUser(UsersRequestDto requestDto) {
        Users users = new Users();
        users.setUserId(requestDto.getUserId());
        users.setName(requestDto.getName());
        users.setEmail(requestDto.getEmail());
        users.setPassword(requestDto.getPassword());

        Users saveUser = usersRepository.save(users);
        return new UsersResponseDto(saveUser);
    }


    public UsersResponseDto getUser(String userId, String password) {
        return usersRepository.findUser(userId, password);
    }
}
