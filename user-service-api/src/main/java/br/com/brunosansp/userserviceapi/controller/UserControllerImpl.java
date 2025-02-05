package br.com.brunosansp.userserviceapi.controller;

import br.com.brunosansp.userserviceapi.controller.impl.IUserController;
import br.com.brunosansp.userserviceapi.service.UserService;
import entity.User;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements IUserController {
    
    private final UserService userService;
    
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public List<User> allUsers() {
        return userService.findAll();
    }
    
    @Override
    public ResponseEntity<UserResponse> findById(final String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }
    
    @Override
    public ResponseEntity<Void> save(final CreateUserRequest createUserRequest) {
        userService.save(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
