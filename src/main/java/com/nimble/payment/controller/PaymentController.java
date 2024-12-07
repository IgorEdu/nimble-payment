package com.nimble.payment.controller;

import com.nimble.payment.domain.PaymentCardDTO;
import com.nimble.payment.domain.user.AuthenticationDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PaymentController {

    @PostMapping("/payment/card")
        public String payment(@RequestBody @Valid PaymentCardDTO data) {
            return "payment";
        }
}
