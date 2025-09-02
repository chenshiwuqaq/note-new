package org.com.Entity;

// 自定义Token异常类
public class CustomTokenException extends RuntimeException {
    private final String errorCode;

    public CustomTokenException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
