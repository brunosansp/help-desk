package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.repository.UserRepository;
import entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
