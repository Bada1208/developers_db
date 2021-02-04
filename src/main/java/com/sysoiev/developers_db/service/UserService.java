package com.sysoiev.developers_db.service;

import com.sysoiev.developers_db.model.User;

public interface UserService extends BaseService<User>{

    User register(User user);

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);

    void activate(User user);
}
