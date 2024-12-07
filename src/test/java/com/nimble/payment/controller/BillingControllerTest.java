package com.nimble.payment.controller;

import com.nimble.payment.domain.billing.BillingDTO;
import com.nimble.payment.domain.billing.BillingDetailsDTO;
import com.nimble.payment.service.BillingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingControllerTest {

    @InjectMocks
    private BillingController billingController;

    @Mock
    private BillingService billingService;

    @Mock
    private BillingDetailsDTO billingDetailsDTO;

    @Mock
    private BillingDTO billingDTO;

    @Test
    void testGetSourceBilling() {
        List<BillingDetailsDTO> mockResponse = Collections.singletonList(billingDetailsDTO);
        when(billingService.getSourceBilling()).thenReturn(ResponseEntity.ok(mockResponse));

        ResponseEntity<List<BillingDetailsDTO>> response = billingController.getSourceBilling();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(billingService, times(1)).getSourceBilling();
    }

    @Test
    void testGetSourceBillingByStatus() {
        String status = "PENDING";
        List<BillingDetailsDTO> mockResponse = Collections.singletonList(billingDetailsDTO);
        when(billingService.getSourceBillingByStatus(status)).thenReturn(ResponseEntity.ok(mockResponse));

        ResponseEntity<List<BillingDetailsDTO>> response = billingController.getSourceBilling(status);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(billingService, times(1)).getSourceBillingByStatus(status);
    }

    @Test
    void testGetDestinationBilling() {
        List<BillingDetailsDTO> mockResponse = Collections.singletonList(billingDetailsDTO);
        when(billingService.getDestinationBilling()).thenReturn(ResponseEntity.ok(mockResponse));

        ResponseEntity<List<BillingDetailsDTO>> response = billingController.getDestinationBilling();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(billingService, times(1)).getDestinationBilling();
    }

    @Test
    void testGetDestinationBillingByStatus() {
        String status = "PAID";
        List<BillingDetailsDTO> mockResponse = Collections.singletonList(billingDetailsDTO);
        when(billingService.getDestinationBillingByStatus(status)).thenReturn(ResponseEntity.ok(mockResponse));

        ResponseEntity<List<BillingDetailsDTO>> response = billingController.getDestinationBilling(status);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockResponse, response.getBody());
        verify(billingService, times(1)).getDestinationBillingByStatus(status);
    }

    @Test
    void testCreateBilling() {
        when(billingService.createBilling(any(BillingDTO.class))).thenReturn(ResponseEntity.ok().build());

        ResponseEntity response = billingController.createBilling(billingDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(billingService, times(1)).createBilling(billingDTO);
    }
}
