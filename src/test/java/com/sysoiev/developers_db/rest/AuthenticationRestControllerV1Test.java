package com.sysoiev.developers_db.rest;

import com.sysoiev.developers_db.builder.UserBuilder;
import com.sysoiev.developers_db.dto.AuthenticationRequestDto;
import com.sysoiev.developers_db.dto.UserRegisterDto;
import com.sysoiev.developers_db.model.User;
import com.sysoiev.developers_db.security.jwt.JwtTokenProvider;
import com.sysoiev.developers_db.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationRestControllerV1Test {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationRestControllerV1 authenticationRestController;

    @Test
    public void login() {

        User user = UserBuilder.userDb("userName").build();

        AuthenticationRequestDto userDto = new AuthenticationRequestDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        //when(authenticationManager.authenticate(any())).thenReturn();
        String tokenNumber = "tokenNumber";
        when(userService.findByUsername(userDto.getUsername())).thenReturn(user);
        when(jwtTokenProvider.createToken(user.getUsername(), user.getRoles())).thenReturn(tokenNumber);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.login(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), Objects.requireNonNull(response.getBody()).get("username"));
        assertEquals(tokenNumber, response.getBody().get("token"));

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    public void loginFailed() {

        User user = UserBuilder.userDb("userName").build();

        AuthenticationRequestDto userDto = new AuthenticationRequestDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());

        when(userService.findByUsername(userDto.getUsername())).thenReturn(null);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.login(userDto);

        String message = (String) Objects.requireNonNull(response.getBody()).get("message");
        assertTrue(message.contains("User with username"));
    }

    @Test
    public void registerUserAccount() {

        User user = UserBuilder.userDb("userName").build();

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());
        userRegisterDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByUsername(userRegisterDto.getUsername())).thenReturn(null);
        when(userService.findByPhoneNumber(userRegisterDto.getPhoneNumber())).thenReturn(null);

        ResponseEntity<Map<Object, Object>> response = authenticationRestController.registerUserAccount(userRegisterDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test(expected = BadCredentialsException.class)
    public void failedRegisterUserAccountByExistingUsername() {

        User user = UserBuilder.userDb("userName").build();

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());
        userRegisterDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByUsername(userRegisterDto.getUsername())).thenReturn(user);

        authenticationRestController.registerUserAccount(userRegisterDto);
    }

    @Test(expected = BadCredentialsException.class)
    public void failedRegisterUserAccountByExistingPhoneNumber() {
        User user = UserBuilder.userDb("userName").build();

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setId(user.getId());
        userRegisterDto.setPassword(user.getPassword());
        userRegisterDto.setUsername(user.getUsername());
        userRegisterDto.setPhoneNumber(user.getPhoneNumber());

        when(userService.findByUsername(userRegisterDto.getUsername())).thenReturn(null);
        when(userService.findByPhoneNumber(userRegisterDto.getPhoneNumber())).thenReturn(user);

        authenticationRestController.registerUserAccount(userRegisterDto);
    }


}
