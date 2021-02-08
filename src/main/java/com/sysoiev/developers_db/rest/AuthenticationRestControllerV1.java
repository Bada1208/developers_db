package com.sysoiev.developers_db.rest;

import com.sysoiev.developers_db.dto.AuthenticationRequestDto;
import com.sysoiev.developers_db.dto.UserRegisterDto;
import com.sysoiev.developers_db.model.User;
import com.sysoiev.developers_db.security.jwt.JwtTokenProvider;
import com.sysoiev.developers_db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e ) {
            Map<Object, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<Object, Object>> registerUserAccount(@RequestBody UserRegisterDto userDto) {

        User userExists = userService.findByUsername(userDto.getUsername());

        if (userExists != null ) {
            throw new BadCredentialsException("User with username: " + userDto.getUsername() + " already exists");
        }

        User userExistsByPhoneNumber = userService.findByPhoneNumber(userDto.getPhoneNumber());

        if (userExistsByPhoneNumber != null ) {
            throw new BadCredentialsException("User with phone number: " + userDto.getPhoneNumber() + " already exists");
        }

        userService.register(userDto.toUser());

        Map<Object, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

}
