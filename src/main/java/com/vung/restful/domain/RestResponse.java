package com.vung.restful.domain;

public class RestResponse<T> {
    private String error;
    // message có thể là string, hoặc arrayList
    private Object message;
    private T data;
    private long statusCode;
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public Object getMessage() {
        return message;
    }
    public void setMessage(Object message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }
    
}
