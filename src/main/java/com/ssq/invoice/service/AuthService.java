package com.ssq.invoice.service;

import com.ssq.invoice.security.jwt.JweUtil;
import com.ssq.invoice.security.jwt.JwtUtil;
import com.ssq.invoice.security.jwt.KeyStoreManager;
import com.ssq.invoice.model.AppUser;
import com.ssq.invoice.model.AuthRequest;
import com.ssq.invoice.repo.AppUserRepository;
import com.ssq.invoice.security.CookieConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthService {
    private JweUtil jweUtil;

    private JwtUtil jwtUtil;
    private MongoUserDetailsService mongoUserDetailsService;
    private CookieConfig cookieConfig;

    private KeyStoreManager keyStoreManager;

    private AuthenticationManager authManager;

    private AppUserRepository appUserRepository;

    @Autowired
    public AuthService(JweUtil jweUtil, MongoUserDetailsService mongoUserDetailsService,
                       AuthenticationManager authManager, KeyStoreManager keyStoreManager,
                       JwtUtil jwtUtil, CookieConfig cookieConfig, AppUserRepository appUserRepository) {
        this.jweUtil = jweUtil;
        this.jwtUtil = jwtUtil;
        this.mongoUserDetailsService = mongoUserDetailsService;
        this.authManager = authManager;
        this.keyStoreManager = keyStoreManager;
        this.cookieConfig = cookieConfig;
        this.appUserRepository = appUserRepository;
    }

    public ResponseEntity<AppUser> createAuthTokenAndReturnAppUser(AuthRequest request, HttpServletResponse response) throws IOException {
        String pw = jweUtil.decryptPasswordInJwe(request.getPassword());
        final UserDetails aseUserDetails = mongoUserDetailsService.loadUserByUsername(request.getUsername());

        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(aseUserDetails, pw);
            authentication = authManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (UsernameNotFoundException ex) {
            ex.printStackTrace();
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Email or password is incorrect");
//            return new ResponseEntity<null>(
//                    "Email or password is incorrect",
//                    HttpStatus.BAD_REQUEST
//            );
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Authentication principal: " + authentication.getPrincipal());

        if (authentication != null) {
            final String jwt = jwtUtil.generateToken(aseUserDetails);
//            HashMap<String, String> jwtResponse = new HashMap<>();
//            jwtResponse.put("jwt", jwt);
            // put JWT into Cookie for frontend
            Cookie cookie = cookieConfig.createCookie("jwt", jwt);
            response.addCookie(cookie);

            AppUser loginUser = appUserRepository.findAppUserByUsername(request.getUsername());

            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        } else {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Email or password is incorrect");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Authenticate user, please username password is correct, return JWT token
    public ResponseEntity<String> authenticateUser(String authorization, HttpServletRequest request, HttpServletResponse response) {
        // encrypt username and password with Base64 (not secure enough)
        String basicAuthCredential = authorization.substring("Basic".length()).trim();
        byte[] credentialBytes = Base64.getDecoder().decode(basicAuthCredential);
        final String[] credential = new String(credentialBytes, StandardCharsets.UTF_8).split(":");
        String username = credential[0];
        String password = credential[1];

        UserDetails userDetails = mongoUserDetailsService.loadUserByUsername(username);

        // Token generated from username and password, it is returned if authentication is successful
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        Authentication auth;

        try {
            auth = authManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            final String jwt = jwtUtil.generateToken(userDetails);
            // put JWT into Cookie for frontend
            Cookie cookie = cookieConfig.createCookie("jwt", jwt);
            response.addCookie(cookie);

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (BadCredentialsException badCredentialsException) {
            badCredentialsException.printStackTrace();
            return new ResponseEntity<String>("Email or password is incorrect", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public HashMap<String, String> getPublicKeyData() {
        HashMap<String, String> pKeyResponse = new HashMap<String, String>();
        RSAPublicKey rsaPubKey = (RSAPublicKey) keyStoreManager.getPublicKey();
        byte[] modulusByte = rsaPubKey.getModulus().toByteArray(); // Get Modulus of Public key;
        // Format the modulus into byte format (e.g. AB:CD:E1)
        String modulusByteStr = "";
        for (byte b : modulusByte) {
            modulusByteStr += String.format("%02X:", b);
        }
        modulusByteStr = modulusByteStr.substring(0, modulusByteStr.length() - 1);
        // base64 encoded format of public key
        pKeyResponse.put("key", Base64.getMimeEncoder().encodeToString(rsaPubKey.getEncoded()));
        // Modulus string of Public key;
        pKeyResponse.put("n", modulusByteStr);
        // public exponent of RSAPublicKey
        pKeyResponse.put("e", rsaPubKey.getPublicExponent().toString());
        return pKeyResponse;

    }

    public void setAuthentication(String username,String jwt,HttpServletRequest request) {
        List<GrantedAuthority> authorities = jwtUtil.extractAuthoritiesFromJwt(jwt);
        UsernamePasswordAuthenticationToken userPasswordAuthToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        Authentication authentication = userPasswordAuthToken;
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public ResponseEntity<String> deleteAuthToken(HttpServletResponse response) {
        Cookie cookie = cookieConfig.createCookie("jwt", null);
        response.addCookie(cookie);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
