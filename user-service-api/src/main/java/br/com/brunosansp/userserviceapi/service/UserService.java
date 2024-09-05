package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.mapper.UserMapper;
import br.com.brunosansp.userserviceapi.repository.UserRepository;
import entity.User;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    
    public UserResponse findById(String id) {
        return userMapper.fromEntity(
            userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Object not found. " +
                    "\nCould not find object for id: {id}, Type: " + UserResponse.class.getName())
            )
        );
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
