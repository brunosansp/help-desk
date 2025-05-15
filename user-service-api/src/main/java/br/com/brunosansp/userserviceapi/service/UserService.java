package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.entity.User;
import br.com.brunosansp.userserviceapi.mapper.IUserMapper;
import br.com.brunosansp.userserviceapi.repository.IUserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;
    
    public UserService(IUserRepository userRepository, IUserMapper userMapper) {
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
    
    public UserResponse update(final String id, final UpdateUserRequest updateUserRequest) {
        User user = find(id);
        verifyIfEmailAlreadyExists(user.getEmail(), user.getId());
        return userMapper.fromEntity(userRepository.save(userMapper.update(updateUserRequest, user)));
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
