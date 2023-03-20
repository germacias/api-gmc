package com.nisum.test.apigmc.domain.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PhoneDto {

    private String number;
    private String cityCode;
    private String countryCode;

    public PhoneDto() {
    }

    public PhoneDto(String number, String countryCode, String cityCode) {
        this.number = number;
        this.countryCode = countryCode;
        this.cityCode = cityCode;
    }
}
