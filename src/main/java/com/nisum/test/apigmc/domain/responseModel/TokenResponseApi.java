package com.nisum.test.apigmc.domain.responseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class TokenResponseApi {
    private String token;

    public TokenResponseApi() {
    }
}
