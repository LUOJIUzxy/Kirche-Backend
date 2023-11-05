package com.ssq.invoice.controller;

import com.ssq.invoice.model.HttpResponse;
import com.ssq.invoice.service.BasicInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/basic-information")
public class BasicInformationController {
    @Autowired
    BasicInformationService basicInformationService;

    @GetMapping("/contact")
    public String getContactInformation() {
        return basicInformationService.getContactInformation();
    }

    @PostMapping("/contact/{newContact}")
    public ResponseEntity<HttpResponse> updateContactInformation(@PathVariable("newContact") String newContact) {
        String updatedContact = basicInformationService.setContactInformation(newContact);
        return response(OK, updatedContact);
    }


    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}