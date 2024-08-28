package br.com.brunosansp.userserviceapi.controller;

import br.com.brunosansp.userserviceapi.service.UserService;
import entity.User;
import models.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.findAll();
    }
    
    @GetMapping("user/{id}")
    public ResponseEntity<UserResponse> byId(@PathVariable String id) {
        UserResponse user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
