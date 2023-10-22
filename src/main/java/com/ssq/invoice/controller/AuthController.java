package com.ssq.invoice.controller;


import com.ssq.invoice.model.AppUser;
import com.ssq.invoice.model.AuthRequest;
import com.ssq.invoice.repo.AppUserRepository;
import com.ssq.invoice.service.AuthService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AppUserRepository appUserRepository;

/*
    // authenticate with username, password in plaintext
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authorization,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception{
        return authService.authenticateUser(authorization,request,response);
    }
*/

    // authenticate with username, password in jwe (username plaintext, password encrypted)
    @PostMapping("/jwe")
    public ResponseEntity<AppUser> loginJwe(@RequestBody AuthRequest loginRequest, HttpServletResponse response) throws IOException {
        return authService.createAuthTokenAndReturnAppUser(loginRequest,response);
    }

    // send public key to frontend, so frontend can use it to encrypt password
    @GetMapping("/pkey")
    public ResponseEntity<HashMap<String,String>> getPublicKey() throws Exception {
        HashMap<String, String> pKeyData = authService.getPublicKeyData();
        return new ResponseEntity<HashMap<String, String>>(pKeyData, HttpStatus.OK);
    }

    // send CSRF token to frontend, used in POST,DELETE,PUT requests
    @GetMapping("/csrf")
    public ResponseEntity<String> csrf() throws Exception {
        return new ResponseEntity<String>("{}",HttpStatus.OK);
    }

    @DeleteMapping("/clean")
    public ResponseEntity<String> cleanJWTToken(HttpServletResponse response) {
        return authService.deleteAuthToken(response);
    }

    @GetMapping("/validate-jwt")
    public ResponseEntity<AppUser> getUserFromJWT() throws JwtException {
        try{
            String username = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            AppUser user = appUserRepository.findAppUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new JwtException("Invalid JWT is sent to back end.");
        }
    }


}
