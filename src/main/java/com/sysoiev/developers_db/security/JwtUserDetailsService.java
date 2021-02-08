package com.sysoiev.developers_db.security;

import com.sysoiev.developers_db.model.Status;
import com.sysoiev.developers_db.model.User;
import com.sysoiev.developers_db.security.jwt.JwtUser;
import com.sysoiev.developers_db.security.jwt.JwtUserFactory;
import com.sysoiev.developers_db.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null || user.getStatus() == Status.DELETED) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        if (user.getStatus() == Status.NOT_ACTIVE) {
            throw new UsernameNotFoundException("User with username: " + username + " not verified");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }
}