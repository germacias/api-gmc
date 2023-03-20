package com.nisum.test.apigmc.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nisum.test.apigmc.domain.entity.Phone;
import com.nisum.test.apigmc.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
//import org.springframework.security.core.Authentication;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String name;

    @NotNull(message = "{value.not.null}")
    @NotEmpty(message = "{value.not.empty}")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
            , message = "{email.invalid}")
    private String email;

    /*
    * ^               # inicio con una letra
    (?=.*[0-9])       # al menos un digito
    (?=.*[a-z])       # al menos una letra minuscula
    (?=.*[A-Z])       # al menos una letra mayuscula
    (?=.*[@#$%^&+=*]) # al menos un caracter especial
    (?=\S+$)          # sin espacios en blancos
    .{8,}             # al menos 8 caracteres
    $                 # fin
    * */
    @NotNull(message = "{value.not.null}")
    @NotEmpty(message = "{value.not.empty}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}$"
            ,message = "{value.pattern.notvalid}")
    private String password;

    private List<PhoneDto> phones;

    private boolean isActive;
    private LocalDateTime lastLogin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UserDto() {
    }

    public UserDto(String name, String email, String password, boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }

    public static User convertToUser(UserDto userDto){
        User user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        HashSet<Phone> phonesSet = new HashSet<>();
        for(PhoneDto phoneDto : userDto.getPhones()){
            Phone phone = new Phone(phoneDto.getNumber(),phoneDto.getCityCode(),phoneDto.getCountryCode(),user);
            phonesSet.add(phone);
        }
        user.setPhones(phonesSet);

        return user;

    }

    public static UserDto convertUserToDto(User user){
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        //dto.setToken(user.getToken());
        dto.setPhones(new ArrayList<>());
        for(Phone ph : user.getPhones()){
            PhoneDto phoneDto = new PhoneDto(ph.getNumber(),ph.getCountrycode(),ph.getCitycode());
            dto.getPhones().add(phoneDto);
        }
        dto.setActive(user.isActive());
        dto.setLastLogin(user.getLastLogin()
                .toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        return dto;
    }
}
