package com.ssq.invoice.security;

import com.ssq.invoice.constant.SecurityConstant;
import com.ssq.invoice.security.filter.JwtAccessDeniedHandler;
import com.ssq.invoice.security.filter.JwtAuthenticationEntryPoint;
import com.ssq.invoice.security.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


/**
 * detailed configuration file
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // security is on method level
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

//    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // tell which user detail service we are using
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
//                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override // cannot name is authenticationManager, because this name has been used in UserResource class already
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // pass everything we want the spring security to manage
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CookieCsrfTokenRepository customCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        customCsrfTokenRepository.setCookieName("XSRF-TOKEN");
        http
                .cors()
                .and()
                .csrf()
                .csrfTokenRepository(customCsrfTokenRepository) // setting up CSRF token to be included in cookie
                .and()
                // allow predefined URLs
                .authorizeRequests()
                .antMatchers(SecurityConstant.PUBLIC_URLS).permitAll()
                // other URLs need to be authenticated
                .anyRequest().authenticated()
                // use customized AccessDeniedHandler
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    }


}
