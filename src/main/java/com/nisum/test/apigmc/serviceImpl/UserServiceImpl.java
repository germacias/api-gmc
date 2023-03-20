package com.nisum.test.apigmc.serviceImpl;

import com.nisum.test.apigmc.domain.dto.UserDto;
import com.nisum.test.apigmc.domain.entity.Phone;
import com.nisum.test.apigmc.domain.entity.User;
import com.nisum.test.apigmc.exception.AttemptAuthenticationException;
import com.nisum.test.apigmc.exception.UserApiException;
import com.nisum.test.apigmc.repository.UserRepo;
import com.nisum.test.apigmc.service.UserService;
import com.nisum.test.apigmc.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
    }

    @Override
    public List<User> findall() {
        return (List<User>) userRepo.findAllUsers();
    }

    @Override
    public User saveUser(UserDto userDto) {
        Optional<User> user = Optional.empty();

        user = userRepo.findByEmail(userDto.getEmail());

        user.ifPresent(x->{
            throw new UserApiException("usuario.existente", HttpStatus.FORBIDDEN);
        });

        user = Optional.of(UserDto.convertToUser(userDto));

        UUID uuid = UUID.randomUUID();

        //Audit
        if(user.get().getCreatedBy()==null){
            user.get().setCreatedBy(uuid);
            user.get().setCreated(new Date());
            if(!user.get().getPhones().isEmpty()){
                for(Phone ph : user.get().getPhones()){
                    ph.setCreated(new Date());
                    ph.setCreatedBy(uuid);
                }
            }
        }else{
            user.get().setModifiedBy(uuid);
            user.get().setModified(new Date());
            if(!user.get().getPhones().isEmpty()){
                for(Phone ph : user.get().getPhones()){
                    ph.setModified(new Date());
                    ph.setModifiedBy(uuid);
                }
            }
        }

        user.get().setLastLogin(new Date());
        userRepo.save(user.get());

        return user.get();
    }

    @Transactional
    @Override
    public Optional<String> autenticate(String email, String password) {
        Optional<String> token = Optional.empty();
        Optional<User> user = this.userRepo.findByEmail(email);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            token = Optional.ofNullable(TokenUtils.createToken(email,password));

            if(!user.get().isActive() || user.get().getToken()==null || TokenUtils.isExpired(user.get().getToken())){
                if(token.isPresent()){
                    user.get().setToken(token.get());
                    user.get().setActive(true);
                }
            }
            user.get().setLastLogin(new Date());
            this.userRepo.save(user.get());
        } catch (AuthenticationException e){
            throw new AttemptAuthenticationException("usuario.logueado.error", e, HttpStatus.UNAUTHORIZED);
        }


        return token;
    }


}
