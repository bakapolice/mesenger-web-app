//package com.example.chatservice.security;
//
//import com.example.chatservice.service.RestService;
//import lombok.RequiredArgsConstructor;
//import org.apache.tomcat.util.json.JSONParser;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class SecurityFilter extends OncePerRequestFilter {
//
//    private final RestService restService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        System.err.println("ACCESS " + request.toString());
//        HttpStatus status = restService.validateRequest(request.getHeader(HttpHeaders.AUTHORIZATION));
//        System.err.println(status.value());
//        response.setStatus(status.value());
//        filterChain.doFilter(request, response);
//    }
//}
