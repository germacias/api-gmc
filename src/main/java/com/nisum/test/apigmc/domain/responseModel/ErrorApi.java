package com.nisum.test.apigmc.domain.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorApi {
    private static final String USER_API_EXCEPTION_PREFIX = "US.";

    private String title;
    private String detail;
    private HttpStatus status;
    private String type;
    private String code;

    @JsonProperty("elapsed_time")
    private LocalDateTime elapsedTime;

    public ErrorApi() {
        this.elapsedTime = LocalDateTime.now();
    }

    public ErrorApi(String title, String detail, String type) {
        this.title = title;
        this.detail = detail;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.type = type;
        this.code = USER_API_EXCEPTION_PREFIX + HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.elapsedTime = LocalDateTime.now();
    }

    public ErrorApi(String title, String detail, HttpStatus status, String type, String code) {
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.type = type;
        this.code = code;
        this.elapsedTime = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(LocalDateTime elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @Override
    public String toString() {
        return "ErrorApi{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
