package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.mapper.UserMapper;
import br.com.brunosansp.userserviceapi.repository.UserRepository;
import entity.User;
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
            userRepository.findById(id).orElse(null)
        );
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
