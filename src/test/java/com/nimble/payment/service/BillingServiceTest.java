package com.nimble.payment.service;

import com.nimble.payment.domain.billing.Billing;
import com.nimble.payment.domain.billing.BillingDTO;
import com.nimble.payment.domain.billing.BillingDetailsDTO;
import com.nimble.payment.domain.user.User;
import com.nimble.payment.repositories.BillingRepository;
import com.nimble.payment.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @InjectMocks
    private BillingService billingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @Mock
    private Billing billing;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
    }

    @Test
    void testGetSourceBilling_UserNotFound() {
        when(userRepository.findByEmailOrCpf("testUser")).thenReturn(null);

        ResponseEntity response = billingService.getSourceBilling();

        assertEquals(404, response.getStatusCodeValue());
        verify(userRepository, times(1)).findByEmailOrCpf("testUser");
        verifyNoInteractions(billingRepository);
    }

    @Test
    void testGetSourceBilling_Success() {
        when(userRepository.findByEmailOrCpf("testUser")).thenReturn(user);


        when(billingRepository.findAllBySourceUserId(user.getId())).thenReturn(List.of(billing));

        ResponseEntity<List<BillingDetailsDTO>> response = billingService.getSourceBilling();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(billingRepository, times(1)).findAllBySourceUserId(user.getId());
    }

    @Test
    void testCreateBilling_UserNotFound() {
        when(userRepository.findByEmailOrCpf("testUser")).thenReturn(null);

        BillingDTO billingDTO = new BillingDTO("12345678900", 100.0, "Payment");
        ResponseEntity response = billingService.createBilling(billingDTO);

        assertEquals(404, response.getStatusCodeValue());
        verify(userRepository, times(1)).findByEmailOrCpf("testUser");
    }

    @Test
    void testCreateBilling_DestinationUserNotFound() {
        User mockUser = new User();
        mockUser.setCpf("12345678900");
        when(userRepository.findByEmailOrCpf("testUser")).thenReturn(mockUser);

        when(userRepository.findByEmailOrCpf("98765432100")).thenReturn(null);

        BillingDTO billingDTO = new BillingDTO("98765432100", 100.0, "Payment");
        ResponseEntity response = billingService.createBilling(billingDTO);

        assertEquals(404, response.getStatusCodeValue());
        verify(userRepository, times(1)).findByEmailOrCpf("98765432100");
    }

    @Test
    void testCreateBilling_Success() {
        User sourceUser = new User();
        sourceUser.setCpf("12345678900");
        when(userRepository.findByEmailOrCpf("testUser")).thenReturn(sourceUser);

        User destinationUser = new User();
        destinationUser.setCpf("98765432100");
        when(userRepository.findByEmailOrCpf("98765432100")).thenReturn(destinationUser);

        BillingDTO billingDTO = new BillingDTO("98765432100", 100.0, "Payment");

        ResponseEntity response = billingService.createBilling(billingDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(billingRepository, times(1)).save(any(Billing.class));
    }

    @Test
    void testGetSourceBillingByStatus_Success() {
        when(userRepository.findByEmailOrCpf("testUser")).thenReturn(user);

        when(billingRepository.findAllBySourceUserIdAndStatus(user.getId(), "PENDING"))
                .thenReturn(List.of(billing));

        ResponseEntity<List<BillingDetailsDTO>> response = billingService.getSourceBillingByStatus("PENDING");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(billingRepository, times(1)).findAllBySourceUserIdAndStatus(user.getId(), "PENDING");
    }
}
