package com.nisum.test.apigmc.service;

import com.nisum.test.apigmc.domain.dto.UserDto;
import com.nisum.test.apigmc.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findall();
    User saveUser(UserDto userDto);
    Optional<String> autenticate(String email, String password);
}
