package com.example.messenger.security.service;

import com.example.messenger.entity.UserEntity;
import com.example.messenger.mapper.UserToEntityMapper;
import com.example.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserToEntityMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLogin(login)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: " + login));
        return UserDetailsImpl.build(userMapper.userEntityToUser(user));
    }
}
