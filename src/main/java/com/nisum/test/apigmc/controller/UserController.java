package com.nisum.test.apigmc.controller;

import com.nisum.test.apigmc.domain.dto.LoginDto;
import com.nisum.test.apigmc.domain.dto.UserDto;
import com.nisum.test.apigmc.domain.entity.User;
import com.nisum.test.apigmc.domain.responseModel.ApplicationResponseApi;
import com.nisum.test.apigmc.domain.responseModel.TokenResponseApi;
import com.nisum.test.apigmc.service.UserService;
import com.nisum.test.apigmc.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(
        name = "REST APIs for User Resource",
        description = "REST APIs - Create User, Login User, Get All Users"
)
@RestController
@RequestMapping(value = "${api.path}")
public class UserController {

    private final MessageSource messageSource;
    private final UserService userService;

    @Autowired
    public UserController(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @Operation(summary = "Get all the system users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "return list of users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) })})
   @GetMapping(value = "${user.path.list}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationResponseApi<List<UserDto>>> getAllUsers() {
        ApplicationResponseApi<List<UserDto>> applicationResponseApi = new ApplicationResponseApi<>();
        List<User> users = this.userService.findall();

        applicationResponseApi.setBody(users.stream().map(UserDto::convertUserToDto).collect(Collectors.toList()));

        return ResponseEntity.status(HttpStatus.OK).body(applicationResponseApi);
    }

    @PostMapping(   value = "${user.path.register}",
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationResponseApi<UserDto>> register(@Valid @RequestBody UserDto userDto) {
        ApplicationResponseApi<UserDto> applicationResponseApi = new ApplicationResponseApi<>();
        User user = this.userService.saveUser(userDto);

        if(user.getId()!=null){
            applicationResponseApi.setBody(UserDto.convertUserToDto(user));
            applicationResponseApi.setMessage(MessageUtils.getMessage(this.messageSource,"usuario.registrado.ok"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponseApi);
    }

    @PostMapping(value = "${user.path.login}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseApi> login(@Valid @RequestBody LoginDto request) {
        TokenResponseApi responseApi = new TokenResponseApi();
        Optional<String> tokenOptional = this.userService.autenticate(request.getEmail(), request.getPassword());

        tokenOptional.ifPresent(responseApi::setToken);

        return ResponseEntity.status(HttpStatus.OK).body(responseApi);
    }
}
