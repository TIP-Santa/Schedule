package com.sparta.scheduleproject.controller;

import com.sparta.scheduleproject.dto.UsersRequestDto;
import com.sparta.scheduleproject.dto.UsersResponseDto;
import com.sparta.scheduleproject.service.UsersService;
import org.springframework.web.bind.annotation.*;

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
    // user/{userId}/password/{password}
    // 특정 유저만 조회하는 것이기 때문에 List 가 아니라 단건 조회 형식으로 변경
    @GetMapping("/{userId}/password/{password}")
    public UsersResponseDto getUser(@PathVariable String userId, @PathVariable String password) {
        return usersService.getUser(userId, password);
    }

    // 유저 정보 수정
    // id와 password 가 일치해야 수정 가능
    // user/{userId}/password/{password}
    @PutMapping("/{userId}/password/{password}")
    public String modifiedUser(
            @PathVariable String userId,
            @PathVariable String password,
            @RequestBody UsersRequestDto requestDto) {
        System.out.println("입력된 비밀번호 : " + password);
        return usersService.updateUser(userId, password, requestDto);
    }

    // 유저 정보 삭제
    @DeleteMapping("/{userId}/password/{password}")
    public String deleteUser(@PathVariable String userId, @PathVariable String password) {
        return usersService.deleteUser(userId, password);
    }



}
