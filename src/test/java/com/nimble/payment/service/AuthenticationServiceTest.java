package com.nimble.payment.service;

import com.nimble.payment.domain.User;
import com.nimble.payment.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        // Arrange
        String testUsername = "12345678900";

        when(userRepository.findByEmailOrCpf(testUsername)).thenReturn((User) user);

        authenticationService.userRepository = userRepository;

        // Act
        UserDetails userDetails = authenticationService.loadUserByUsername(testUsername);

        // Assert
        assertNotNull(userDetails);
        assertEquals(user, userDetails);
        verify(userRepository, times(1)).findByEmailOrCpf(testUsername);
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        String testUsername = "12345678900";

        when(userRepository.findByEmailOrCpf(testUsername)).thenReturn(null);
        authenticationService.userRepository = userRepository;

        // Act & Assert
        assertNull(authenticationService.loadUserByUsername(testUsername));

        verify(userRepository, times(1)).findByEmailOrCpf(testUsername);
    }
}