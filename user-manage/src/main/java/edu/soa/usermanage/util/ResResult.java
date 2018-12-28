package edu.soa.usermanage.util;


import java.io.Serializable;

/**
 * 返回结果
 * Created by liuwu on 2018/2/28 0028.
 */
public class ResResult<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T data;

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
}
