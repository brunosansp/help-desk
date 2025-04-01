package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.entity.User;
import br.com.brunosansp.userserviceapi.mapper.UserMapper;
import br.com.brunosansp.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
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
            find(id)
        );
    }
    
    public List<UserResponse> findAll() {
        return userRepository.findAll()
            .stream().map(userMapper::fromEntity)
            .toList();
    }
    
    public void save(CreateUserRequest createUserRequest) {
        verifyIfEmailAlreadyExists(createUserRequest.email(), null);
        userRepository.save(userMapper.fromRequest(createUserRequest));
    }
    
    private User find(final String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Object not found. Id: " + id + ", Type: " + UserResponse.class.getSimpleName()
            ));
    }
    
    private void verifyIfEmailAlreadyExists(final String email, final String id) {
        userRepository.findByEmail(email)
            .filter(user -> !user.getId().equals(id))
            .ifPresent(user -> {
                throw new DataIntegrityViolationException("Email [ " + email + " ] already exists");
            });
    }
}
