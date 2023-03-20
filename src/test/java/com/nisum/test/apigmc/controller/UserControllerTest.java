package com.nisum.test.apigmc.controller;

import com.nisum.test.apigmc.domain.dto.PhoneDto;
import com.nisum.test.apigmc.domain.dto.UserDto;
import com.nisum.test.apigmc.domain.entity.Phone;
import com.nisum.test.apigmc.domain.entity.User;
import com.nisum.test.apigmc.domain.responseModel.ApplicationResponseApi;
import com.nisum.test.apigmc.repository.UserRepo;
import com.nisum.test.apigmc.service.UserService;
import com.nisum.test.apigmc.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    MessageSource messageSource;

    @Test
    void register_should_return_success(){
        UserController userController = new UserController(this.messageSource,this.userService);
        User user = mock(User.class);

        Mockito.when(userService.saveUser(any())).thenReturn(user);

        ResponseEntity<ApplicationResponseApi<UserDto>> userResponse = userController.register(createDummyUserDto());

        assertEquals(HttpStatus.CREATED,userResponse.getStatusCode());

    }

    private UserDto createDummyUserDto(){
        UserDto dto = new UserDto("German Macis","germacias@gmail.com","admin",true);
        PhoneDto phoneDto = new PhoneDto("22223105","01","+505");
        dto.setPhones(new ArrayList<>());
        dto.getPhones().add(phoneDto);
        Date date = new Date();
        dto.setLastLogin(date
                .toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime());
        return dto;
    }



}