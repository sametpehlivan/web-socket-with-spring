package com.sametp.wsocket.controller;

import com.sametp.wsocket.models.User;
import com.sametp.wsocket.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User addUser(@Payload User user){
        userService.create(user);
        return user;
    }
    @MessageMapping("/user.disconnect")
    @SendTo("/user/topic")
    public User disconnect(@Payload User user){
        userService.disconnect(user);
        return user;
    }
    @GetMapping("/users")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUser(){
        return userService.findConnectedUsers();
    }
}
