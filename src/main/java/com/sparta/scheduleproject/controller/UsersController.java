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
    // 입력해야 할 데이터 : userId, name, email, password
    // 입력 형식 : {"userId" : "userId", "name" : "name", "email" : "email", "password" : "password"}
    // URL : localhost:8080/user
    @PostMapping
    public UsersResponseDto createUser(@RequestBody UsersRequestDto requestDto){
        System.out.println("Received userId: " + requestDto.getUserId());
        return usersService.createUser(requestDto);
    }

    // 유저 정보 조회
    // id와 password 로 정보 조회
    // 특정 유저만 조회하는 것이기 때문에 List 가 아니라 단건 조회 형식으로 변경
    // URL : localhost:8080/user?userId={userid}&password={password}
    @GetMapping
    public UsersResponseDto getUser(@RequestParam String userId, @RequestParam String password) {
        return usersService.getUser(userId, password);
    }

    // 유저 정보 수정
    // id와 password 가 일치해야 수정 가능
    // URL : localhost:8080/user?userid={userid}&password={password}
    @PutMapping
    public String modifiedUser(
            @RequestParam String userId,
            @RequestParam String password,
            @RequestBody UsersRequestDto requestDto) {
        System.out.println("입력된 비밀번호 : " + password);
        return usersService.updateUser(userId, password, requestDto);
    }

    // 유저 정보 삭제
    // id와 password 가 일치해야 삭제 가능
    // URL : localhost:8080/user?userid={userid}&password={password}
    @DeleteMapping
    public String deleteUser(@RequestParam String userId, @RequestParam String password) {
        return usersService.deleteUser(userId, password);
    }
}
