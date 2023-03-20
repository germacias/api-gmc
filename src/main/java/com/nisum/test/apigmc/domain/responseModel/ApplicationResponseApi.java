package com.nisum.test.apigmc.domain.responseModel;

public class ApplicationResponseApi<T> {
    private Object message;
    private ErrorApi error;
    private T body;

    public ApplicationResponseApi() {
    }

    public ApplicationResponseApi(Object message, ErrorApi error, T body) {
        this.message = message;
        this.error = error;
        this.body = body;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public ErrorApi getError() {
        return error;
    }

    public void setError(ErrorApi error) {
        this.error = error;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ResponseApi{" +
                "message=" + message +
                ", error=" + error +
                ", body=" + body +
                '}';
    }
}
