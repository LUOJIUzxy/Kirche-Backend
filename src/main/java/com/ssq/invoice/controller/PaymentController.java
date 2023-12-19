package com.ssq.invoice.controller;

import com.ssq.invoice.model.dao.Company;
import com.ssq.invoice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stripe")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

  //  @GetMapping("")
   // @PreAuthorize("hasAnyAuthority('companies:read')")
    @GetMapping("/link")
    public String createPaymentLink() throws StripeException {
        return paymentService.createPayment();
    }

}
