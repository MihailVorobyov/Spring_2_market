package com.geekbrains.geekmarketwinter.services;

import com.geekbrains.geekmarketwinter.entities.SystemUser;
import com.geekbrains.geekmarketwinter.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
