package com.example.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestService {
    private final RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8080/api/auth/validate";

    public HttpStatus validateRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        System.out.println(headers);
        HttpEntity request = new HttpEntity(null, headers);

        try {
            this.restTemplate.exchange(url, HttpMethod.GET, request, String.class, 1);
            return HttpStatus.OK;
        } catch (HttpClientErrorException ex){
            return ex.getStatusCode();
        }
    }
}
