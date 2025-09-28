package kh.com.foss.sample.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Throwable {
    private final String code;

    public BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
