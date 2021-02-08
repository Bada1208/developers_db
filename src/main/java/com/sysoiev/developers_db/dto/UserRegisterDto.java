package com.sysoiev.developers_db.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sysoiev.developers_db.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterDto {
    private Long id;
    private String username;
    private String phoneNumber;
    private String password;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        return user;
    }

}