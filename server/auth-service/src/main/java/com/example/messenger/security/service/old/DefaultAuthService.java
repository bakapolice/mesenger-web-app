package com.example.messenger.security.service.old;

import com.example.messenger.entity.RefreshTokenEntity;
import com.example.messenger.entity.UserEntity;
import com.example.messenger.exception.TokenRefreshException;
import com.example.messenger.mapper.RefreshTokenToEntityMapper;
import com.example.messenger.models.RefreshToken;
import com.example.messenger.payload.request.JwtRequest;
import com.example.messenger.payload.response.JwtResponse;
import com.example.messenger.payload.response.RefreshJwtResponse;
import com.example.messenger.security.jwt.JwtUtils;
import com.example.messenger.security.service.AuthService;
import com.example.messenger.security.service.RefreshTokenService;
import com.example.messenger.security.service.UserDetailsImpl;
import com.example.messenger.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenToEntityMapper refreshTokenMapper;

    private final UserService userService;


    @Override
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest loginRequest) {

        UserEntity user = userService.checkCredentialsByLogin(loginRequest.getLogin(), loginRequest.getPassword());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenMapper.refreshTokenEntityToRefreshToken(
                refreshTokenService.createRefreshToken(userDetails.getId()));
        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken.getToken(), userDetails.getId().toString(),
                userDetails.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), roles));
    }

    @Override
    public ResponseEntity<RefreshJwtResponse> refresh(String refreshToken) {

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getLogin());
                    return ResponseEntity.ok(new RefreshJwtResponse(token, refreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Refresh token is not in database!"));
    }
}

