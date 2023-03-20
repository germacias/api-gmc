package com.nisum.test.apigmc.serviceImpl;

import com.nisum.test.apigmc.domain.dto.PhoneDto;
import com.nisum.test.apigmc.domain.dto.UserDto;
import com.nisum.test.apigmc.domain.entity.Phone;
import com.nisum.test.apigmc.domain.entity.User;
import com.nisum.test.apigmc.repository.UserRepo;
import com.nisum.test.apigmc.service.UserService;
import com.nisum.test.apigmc.utils.TokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MTg0MjU4NywibmFtZSI6Imdl" +
            "cm1hY2lhc0BnbWFpbC5jb20iLCJlbWFpbCI6ImFkbWluIn0.2ovyXZrlNs6btf7U2Xyt4Xav_JqHh9As" +
            "ojNlZN-UXcwZGGjl0k-Pn68koOPE_nxK-nN3dj5rakoPupnAuwEH4w";

    private static final String ENCODED_PASSWORD = "$2a$10$Y3Sz3Dx1PCOjBKyRQS4MleibXNx8rq/URcP3guWOQwch4XdupIs2.";

    private User userAudit = new User();
    private UserDto userDto= new UserDto();

    UserRepo userRepoTrans;

    @Mock
    UserRepo userRepo;

    @Mock
    AuthenticationManager authenticationManager;

    UserService userService;


    @Test
    void saveUser_should_return_User_andNotActive() {
        userService = new UserServiceImpl(authenticationManager, userRepo);
        Optional<User> user = Optional.empty();

        Mockito.when(this.userRepo.findByEmail(any())).thenReturn(user);
        User userSaved = this.userService.saveUser(getDummyRequest());

        Assertions.assertEquals(getDummyRequest().getEmail(), userSaved.getEmail());
        assert(!userSaved.isActive());
    }

    @Test
    void findall_should_return_UsersList() {
        userService = new UserServiceImpl(authenticationManager, userRepo);
        List<User> userExpected = this.userRepo.findAllUsers();
        List<User> userResponse = this.userService.findall();

        Assertions.assertEquals(userExpected,userResponse);
    }

    private UserDto getDummyRequest() {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber(getDummyPhoneEntity(this.userAudit).get().getNumber());
        phoneDto.setCityCode(getDummyPhoneEntity(this.userAudit).get().getCitycode());
        phoneDto.setCountryCode(getDummyPhoneEntity(this.userAudit).get().getCountrycode());

        UserDto userDto = new UserDto();
        userDto.setName("Alicia Rueda Mantilla");
        userDto.setEmail("aruedamantilla@gmail.com");
        userDto.setPassword("Alicia123*");
        userDto.setPhones(Collections.singletonList(phoneDto));

        return userDto;
    }

    private Optional<Phone> getDummyPhoneEntity(User userAudit) {
        Phone phone = new Phone();
        phone.setId(20L);
        phone.setNumber("22223100");
        phone.setCitycode("1");
        phone.setCountrycode("505");
        phone.setCreated(new Date());
        phone.setModified(new Date());
        phone.setCreatedBy(userAudit.getId());
        phone.setModifiedBy(userAudit.getId());

        return Optional.of(phone);
    }
}