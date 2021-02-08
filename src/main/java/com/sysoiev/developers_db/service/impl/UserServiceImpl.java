package com.sysoiev.developers_db.service.impl;

import com.sysoiev.developers_db.model.Role;
import com.sysoiev.developers_db.model.Status;
import com.sysoiev.developers_db.model.User;
import com.sysoiev.developers_db.repository.RoleRepository;
import com.sysoiev.developers_db.repository.UserRepository;
import com.sysoiev.developers_db.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sysoiev.developers_db.utils.DateUtil.getCurrentDate;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.NOT_ACTIVE);

        user.setCreated(getCurrentDate());
        user.setUpdated(getCurrentDate());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public User findByUsername(String username) {
        log.info("IN findByUsername - user: {} found by username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        log.info("IN findByPhoneNumber - user: {} found by phoneNumber: {}", phoneNumber);
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void activate(User user) {
        user.setStatus(Status.ACTIVE);
        user.setUpdated(getCurrentDate());
        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        if (user.getStatus() == null) user.setStatus(Status.ACTIVE);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreated(getCurrentDate());
        user.setUpdated(getCurrentDate());

        List<Role> userRoles = new ArrayList<>();
        for (Role role : user.getRoles()
        ) {
            Role roleUser = roleRepository.findByName(role.getName());
            userRoles.add(roleUser);
        }

        user.setRoles(userRoles);
        userRepository.save(user);
        log.info("IN UserServiceImpl save {}", user);
    }

    @Override
    public void update(Long id, User updatedUser) {
        User user = getById(id);

        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());

        if (updatedUser.getPhoneNumber() != null) user.setPhoneNumber(updatedUser.getPhoneNumber());

        if (updatedUser.getStatus() != null) user.setStatus(updatedUser.getStatus());

        if (updatedUser.getPassword() != null)
            user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));

        if (updatedUser.getRoles() != null) {
            List<Role> userRoles = new ArrayList<>();
            for (Role role : updatedUser.getRoles()
            ) {
                Role roleUser = roleRepository.findByName(role.getName());
                userRoles.add(roleUser);
            }

            user.setRoles(userRoles);
        }

        user.setUpdated(getCurrentDate());

        userRepository.save(user);
        log.info("IN UserServiceImpl update {}", user);
    }

    @Override
    public void delete(Long id) {
        User user = getById(id);
        user.setStatus(Status.DELETED);
        user.setUpdated(getCurrentDate());

        userRepository.save(user);
        log.info("IN UserServiceImpl delete {}", user);
    }

    @Override
    public User getById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN getById - no user found by id: {}", id);
            return null;
        }

        log.info("IN getById - user: {} found by id: {}", result);

        return result;
    }

    @Override
    public List<User> list() {
        log.info("IN UserServiceImpl list");
        return userRepository.findAll();
    }
}
