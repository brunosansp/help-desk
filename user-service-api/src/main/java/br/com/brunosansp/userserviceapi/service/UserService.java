package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.entity.User;
import br.com.brunosansp.userserviceapi.mapper.IUserMapper;
import br.com.brunosansp.userserviceapi.repository.IUserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final IUserRepository repository;
    private final IUserMapper mapper;
    private final BCryptPasswordEncoder encoder;
    
    public UserService(IUserRepository repository, IUserMapper mapper, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }
    
    public UserResponse findById(String id) {
        return mapper.fromEntity(
            find(id)
        );
    }
    
    public List<UserResponse> findAll() {
        return repository.findAll()
            .stream().map(mapper::fromEntity)
            .toList();
    }
    
    public void save(CreateUserRequest request) {
        verifyIfEmailAlreadyExists(request.email(), null);
        repository.save(
            mapper.fromRequest(request).withPassword(encoder.encode(request.password()))
        );
    }
    
    public UserResponse update(final String id, final UpdateUserRequest request) {
        User user = find(id);
        verifyIfEmailAlreadyExists(user.getEmail(), user.getId());
        return mapper.fromEntity(
            repository.save(
                mapper.update(request, user).withPassword(
                    request.password() != null ? encoder.encode(request.password()) : user.getPassword()
                )
            )
        );
    }
    
    private User find(final String id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Object not found. Id: " + id + ", Type: " + UserResponse.class.getSimpleName()
            ));
    }
    
    private void verifyIfEmailAlreadyExists(final String email, final String id) {
        repository.findByEmail(email)
            .filter(user -> !user.getId().equals(id))
            .ifPresent(user -> {
                throw new DataIntegrityViolationException("Email [ " + email + " ] already exists");
            });
    }
}
