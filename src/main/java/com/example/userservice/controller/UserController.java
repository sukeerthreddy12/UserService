package com.example.userservice.controller;

import com.example.userservice.entities.UserInfoDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/v1/createUpdate")
    public ResponseEntity<UserInfoDto> createOrUpdateUser(@RequestBody UserInfoDto userInfo) {

        try{
            UserInfoDto user = userService.createOrUpdateUser(userInfo);
            return new ResponseEntity<>(user , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((UserInfoDto) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/user/v1/getUser")
    public ResponseEntity<UserInfoDto> getUser(@RequestBody UserInfoDto userInfo) {
        try{
            UserInfoDto user = userService.getUser(userInfo);
            return new ResponseEntity<>(user , HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((UserInfoDto) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
