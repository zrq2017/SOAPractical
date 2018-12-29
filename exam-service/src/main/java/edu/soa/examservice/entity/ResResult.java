package edu.soa.examservice.entity;

import java.io.Serializable;

public class ResResult<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T data;

    public static ResResult ok(){
        ResResult result = new ResResult();
        result.setCode(200);
        return result;
    }

    public static ResResult ok(String message){
        ResResult result = new ResResult();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    public static ResResult error(int code,String message){
        ResResult result = new ResResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public ResResult withData(T data){
        this.setData(data);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
