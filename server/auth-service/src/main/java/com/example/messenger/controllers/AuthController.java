package com.example.messenger.controllers;

import com.example.messenger.payload.request.JwtRequest;
import com.example.messenger.payload.response.JwtResponse;
import com.example.messenger.payload.request.RefreshJwtRequest;
import com.example.messenger.payload.response.ErrorResponse;
import com.example.messenger.payload.response.MessageResponse;
import com.example.messenger.payload.response.RefreshJwtResponse;
import com.example.messenger.exception.LoginException;
import com.example.messenger.exception.RegistrationException;
import com.example.messenger.models.User;
import com.example.messenger.security.service.AuthService;
import com.example.messenger.security.service.RefreshTokenService;
import com.example.messenger.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping
    public ResponseEntity<MessageResponse> register (@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshJwtResponse> getNewToken(@RequestBody RefreshJwtRequest request){
        return authService.refresh(request.getRefreshToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(Long userId) {
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @GetMapping("/validate")
    public String validate(){
        return "validate";
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex){
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }
}
