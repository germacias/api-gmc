package com.nisum.test.apigmc.security;

import com.nisum.test.apigmc.domain.entity.User;
import com.nisum.test.apigmc.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findOneByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario con email "+email+" no existe"));

        return new UserDetailsImpl(user);
    }
}
