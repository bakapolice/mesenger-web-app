package com.example.messenger.security.service;

import com.example.messenger.entity.AuthRoleEntity;
import com.example.messenger.entity.UserEntity;
import com.example.messenger.exception.LoginException;
import com.example.messenger.exception.UserNotFoundException;
import com.example.messenger.mapper.AuthRoleToEntityMapper;
import com.example.messenger.mapper.UserToEntityMapper;
import com.example.messenger.models.AuthRole;
import com.example.messenger.models.ERole;
import com.example.messenger.models.User;
import com.example.messenger.payload.response.MessageResponse;
import com.example.messenger.repository.AuthRoleRepository;
import com.example.messenger.repository.UserRepository;
import com.example.messenger.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserToEntityMapper userMapper;
    private final AuthRoleRepository authRoleRepository;
    private final AuthRoleToEntityMapper roleMapper;
    private final PasswordEncoder encoder;


    @Override
    public User getUser(Long id){
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));
        return userMapper.userEntityToUser(userEntity);
    }

    @Override
    public List<User> getAllUsers(){
        Iterable<UserEntity> iterable = userRepository.findAll();

        ArrayList<User> users = new ArrayList<>();
        for(UserEntity userEntity : iterable){
            users.add(userMapper.userEntityToUser(userEntity));
        }
        return users;
    }

    @Override
    public ResponseEntity<MessageResponse> register(User user) {

        if(userRepository.findByLogin(user.getLogin()).isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("User with login: " + user.getLogin() + " already registered!"));
        }

        if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("User with email: " + user.getEmail() + " already registered!"));
        }

        //String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        User newUser = userMapper.userEntityToUser(new UserEntity(null, user.getLogin(), encoder.encode(user.getPassword()), user.getFirstName(),
                user.getLastName(), user.getEmail(), null));

        //Set<String> strRoles = signUpRequest.getRole();
        Set<AuthRole> roles = new HashSet<>();
        if (user.getRoles() == null) {
            AuthRoleEntity userRole = authRoleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(roleMapper.authRoleEntityToAuthRole(userRole));
        } else {
            user.getRoles().forEach(role -> {
                switch (role.getName()) {
                    case ROLE_ADMIN -> {
                        AuthRoleEntity adminRole = authRoleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(roleMapper.authRoleEntityToAuthRole(adminRole));
                    }
                    case ROLE_MODERATOR -> {
                        AuthRoleEntity modRole = authRoleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(roleMapper.authRoleEntityToAuthRole(modRole));
                    }
                    default -> {
                        AuthRoleEntity userRole = authRoleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(roleMapper.authRoleEntityToAuthRole(userRole));
                    }
                }
            });
        }

        newUser.setRoles(roles);
        userRepository.save(userMapper.userToUserEntity(newUser));
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public void checkCredentials(String userId, String userSecret) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(Long.parseLong(userId));
        if(optionalUserEntity.isEmpty())
            throw new LoginException("User with id: " + userId + " not found!");

        UserEntity userEntity = optionalUserEntity.get();
//        if(!BCrypt.checkpw(userSecret, userEntity.getHash()))
//            throw new LoginException("Password is incorrect!");
    }

    @Override
    public UserEntity checkCredentialsByLogin(String login, String userSecret) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByLogin(login);
        if(optionalUserEntity.isEmpty())
            throw new LoginException("User with login: " + login + " not found!");

        UserEntity userEntity = optionalUserEntity.get();
//        if(!BCrypt.checkpw(userSecret, userEntity.getHash()))
//            throw new LoginException("Password is incorrect!");

        return userEntity;
    }

}
