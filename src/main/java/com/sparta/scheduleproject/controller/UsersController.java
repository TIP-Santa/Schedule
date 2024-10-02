package com.sparta.scheduleproject.controller;

import com.sparta.scheduleproject.dto.UsersRequestDto;
import com.sparta.scheduleproject.dto.UsersResponseDto;
import com.sparta.scheduleproject.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // 유저 정보 작성
    @PostMapping
    public UsersResponseDto createUser(@RequestBody UsersRequestDto requestDto){
        System.out.println("Received userId: " + requestDto.getUserId());
        return usersService.createUser(requestDto);
    }

    // 유저 정보 조회
    // id와 password 로 정보 조회
    @GetMapping("/{userId}/password/{password}")
    public UsersResponseDto getUser(@PathVariable String userId, @PathVariable String password) {
        System.out.println("Searching for userId: " + userId + ", password: " + password);
        return usersService.getUser(userId, password);
    }





}
