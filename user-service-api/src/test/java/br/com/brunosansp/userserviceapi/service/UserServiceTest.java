package br.com.brunosansp.userserviceapi.service;

import br.com.brunosansp.userserviceapi.entity.User;
import br.com.brunosansp.userserviceapi.mapper.IUserMapper;
import br.com.brunosansp.userserviceapi.repository.IUserRepository;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

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
        when(mapper.fromEntity(any(User.class))).thenReturn(mock(UserResponse.class));
        
        final var resposne = service.findById("1");
        assertNotNull(resposne);
        assertEquals(UserResponse.class, resposne.getClass());
        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(1)).fromEntity(any(User.class));
    }
}