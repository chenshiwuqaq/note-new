package org.com.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;//状态码
    private String message;//提示信息
    private T data; //相应的数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public static <E> Result<E> success(E data){
        return new Result<E>(0,"success",data);
    }
    public static Result success(){
        return new Result(0,"success",null);
    }
    public static <E>Result<E> error(String message){
        return new Result<E>(1,message,null);
    }
}
