package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.entity.User;
import br.com.brunosansp.userserviceapi.mapper.IUserMapper;
import br.com.brunosansp.userserviceapi.repository.IUserRepository;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.brunosansp.userserviceapi.create.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    
    @InjectMocks
    private UserService service;
    
    @Mock
    private IUserRepository repository;
    
    @Mock
    private IUserMapper mapper;
    
    @Mock
    private BCryptPasswordEncoder encoder;
    
    @Test
    void whenCallFindByIdWithValidThenReturnUserResponse() {
        when(repository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));
        
        final var resposne = service.findById("1");
        assertNotNull(resposne);
        assertEquals(UserResponse.class, resposne.getClass());
        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(1)).fromEntity(any(User.class));
    }
    
    @Test
    void whenCallFindByIdWithInvalidIdThenThrowResourceNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        try {
            service.findById("1");
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
        }
        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(0)).fromEntity(any(User.class));
    }
    
    @Test
    void whenCallFindAllThenReturnListOfUerResponse() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));
        
        List<UserResponse> responses = service.findAll();
        
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(UserResponse.class, responses.get(0).getClass());
        
        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).fromEntity(any(User.class));
    }
}