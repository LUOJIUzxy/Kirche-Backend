package com.ssq.invoice.security.filter;

import com.ssq.invoice.security.jwt.JwtUtil;
import com.ssq.invoice.service.AuthService;
import com.ssq.invoice.service.MongoUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ssq.invoice.constant.SecurityConstant.*;

/**
 * used to authorize the request through filter, it fires every time once the request comes in
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;
//    private JwtTokenProvider jwtTokenProvider;
//
//    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String jwt = null;
//        boolean isBasicAuth = false;
        // action is sent before every request, client needs to collect information from server first


        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            Cookie cookie = WebUtils.getCookie(request, "jwt");
            if (cookie != null && cookie.getValue() != null) {
                jwt = cookie.getValue();
                try {
                    if (jwtUtil.verifyJwtSignature(jwt)) {
                        username = jwtUtil.extractUsername(jwt);
                        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            authService.setAuthentication(username,jwt,request);
                        }
                    }
                } catch (ExpiredJwtException ex) {
                    System.err.println(TOKEN_EXPIRED);
                    response.sendError(HttpStatus.EXPECTATION_FAILED.value(), TOKEN_EXPIRED);
                } catch (Exception ex) {
                    System.err.println(UNMATCHED_SIGNATURE);
                    response.sendError(HttpStatus.EXPECTATION_FAILED.value(), UNMATCHED_SIGNATURE);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
